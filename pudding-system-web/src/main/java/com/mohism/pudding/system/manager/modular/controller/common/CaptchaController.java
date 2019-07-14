package com.mohism.pudding.system.manager.modular.controller.common;


import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.google.gson.Gson;
import com.mohism.pudding.kernel.model.auth.context.CommonConstant;
import com.mohism.pudding.kernel.model.exception.ServiceException;
import com.mohism.pudding.kernel.model.reqres.response.ResponseData;
import com.mohism.pudding.kernel.model.util.CreateVerifyCode;
import com.mohism.pudding.system.manager.core.constants.SettingConstant;
import com.mohism.pudding.system.manager.core.limit.RedisRaterLimiter;
import com.mohism.pudding.system.manager.entity.SysUser;
import com.mohism.pudding.system.manager.modular.service.SysUserService;
import com.mohism.pudding.system.manager.util.IpInfoUtil;
import com.mohism.pudding.system.manager.util.SmsUtil;
import com.mohism.pudding.system.manager.vo.Captcha;
import com.mohism.pudding.system.manager.vo.VaptchaSetting;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 *  滑块验证码控制器
 * </p>
 *
 * @author real earth
 * @since 2019-07-07
 */
@Api(description = "验证码接口")
@RequestMapping("/pudding/common/captcha")
@RestController
@Slf4j
public class CaptchaController {

    @Autowired
    private SmsUtil smsUtil;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private IpInfoUtil ipInfoUtil;

    @Autowired
    private RedisRaterLimiter redisRaterLimiter;

    @Autowired
    private SysUserService userService;

    public VaptchaSetting getVaptchaSetting(){

        String v = redisTemplate.opsForValue().get(SettingConstant.VAPTCHA_SETTING);
        if(StrUtil.isBlank(v)){
            throw new ServiceException(601,"系统还未配置Vaptcha验证码");
        }
        return new Gson().fromJson(v, VaptchaSetting.class);
    }

    @RequestMapping(value = "/init", method = RequestMethod.GET)
    @ApiOperation(value = "初始化验证码")
    public ResponseData initCaptcha() {

        String captchaId = UUID.randomUUID().toString().replace("-","");
        String code = new CreateVerifyCode().randomStr(4);
        Captcha captcha = new Captcha();
        captcha.setCaptchaId(captchaId);
        //缓存验证码
        redisTemplate.opsForValue().set(captchaId,code,3L, TimeUnit.MINUTES);
        return ResponseData.success(captcha);
    }

    @RequestMapping(value = "/draw/{captchaId}", method = RequestMethod.GET)
    @ApiOperation(value = "根据验证码ID获取图片")
    public void drawCaptcha(@PathVariable("captchaId") String captchaId, HttpServletResponse response) throws IOException {

        // 得到验证码 生成指定验证码
        String code=redisTemplate.opsForValue().get(captchaId);
        CreateVerifyCode vCode = new CreateVerifyCode(116,36,4,10,code);
        vCode.write(response.getOutputStream());
    }

    @RequestMapping(value = "/sendSms/{mobile}", method = RequestMethod.GET)
    @ApiOperation(value = "发送短信验证码")
    public ResponseData sendSms(@PathVariable String mobile,
                                  @ApiParam("默认0发送给所有手机号 1只发送给注册手机") @RequestParam(required = false, defaultValue = "0") Integer type,
                                  HttpServletRequest request) {

        if(type==1&&userService.findByMobile(mobile)==null){
            return ResponseData.error("手机号未注册");
        }
        // IP限流 1分钟限1个请求
        String key = "sendSms:"+ipInfoUtil.getIpAddr(request);
        String value = redisTemplate.opsForValue().get(key);
        if (StrUtil.isNotBlank(value)) {
            return ResponseData.error("您发送的太频繁啦，请稍后再试");
        }
        // 生成6位数验证码
        String code = new CreateVerifyCode().getRandomNum();
        // 缓存验证码
        redisTemplate.opsForValue().set(CommonConstant.PRE_SMS + mobile, code,5L, TimeUnit.MINUTES);
        // 发送验证码
        try {
            // 获取模板
            String templateCode = redisTemplate.opsForValue().get(SettingConstant.ALI_SMS_COMMON);
            SendSmsResponse response = smsUtil.sendCode(mobile, code, templateCode);
            if(response.getCode() != null && ("OK").equals(response.getMessage())) {
                // 请求成功 标记限流
                redisTemplate.opsForValue().set(key, "sended", 1L, TimeUnit.MINUTES);
                return ResponseData.success("发送短信验证码成功");
            }else{
                return ResponseData.error("请求发送验证码失败，" + response.getMessage());
            }
        } catch (ClientException e) {
            log.error("请求发送短信验证码失败，" + e);
            return ResponseData.error("请求发送验证码失败，" + e.getMessage());
        }
    }

    @RequestMapping(value = "/sendResetSms", method = RequestMethod.POST)
    @ApiOperation(value = "发送重置密码验证码")
    public ResponseData sendResetSms(@RequestParam String mobile,
                                       @RequestParam String token,
                                       HttpServletRequest request) {

        VaptchaSetting vs = getVaptchaSetting();
        // 验证vaptcha验证码
        String params = "id=" + vs.getVid() + "&secretkey=" + vs.getSecretKey() + "&token=" + token
                + "&ip=" + ipInfoUtil.getIpAddr(request);
        String result = HttpUtil.post(SettingConstant.VAPTCHA_URL, params);
        if(!result.contains("\"success\":1")){
            return ResponseData.error("Vaptcha验证码验证失败");
        }
        SysUser u = userService.findByMobile(mobile);
        if(u==null){
            return ResponseData.error("该手机号未注册");
        }
        // IP限流 1分钟限1个请求
        String key = "sendSms:"+ipInfoUtil.getIpAddr(request);
        String value = redisTemplate.opsForValue().get(key);
        if (StrUtil.isNotBlank(value)) {
            return ResponseData.error("您发送的太频繁啦，请稍后再试");
        }
        // 生成6位数验证码
        String code = new CreateVerifyCode().getRandomNum();
        // 缓存验证码
        redisTemplate.opsForValue().set(CommonConstant.PRE_SMS + mobile, code,5L, TimeUnit.MINUTES);
        // 发送验证码
        try {
            // 获取重置密码模板
            String templateCode = redisTemplate.opsForValue().get(SettingConstant.ALI_SMS_RESET_PASS);
            SendSmsResponse response = smsUtil.sendCode(mobile, code, templateCode);
            if(response.getCode() != null && ("OK").equals(response.getMessage())) {
                // 请求成功 标记限流
                redisTemplate.opsForValue().set(key, "sended", 1L, TimeUnit.MINUTES);
                return ResponseData.error("发送短信验证码成功");
            }else{
                return ResponseData.error("请求发送验证码失败，" + response.getMessage());
            }
        } catch (ClientException e) {
            log.error("请求发送短信验证码失败，" + e);
            return ResponseData.error("请求发送验证码失败，" + e.getMessage());
        }
    }
}
