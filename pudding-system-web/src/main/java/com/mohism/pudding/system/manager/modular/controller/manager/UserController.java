package com.mohism.pudding.system.manager.modular.controller.manager;


import cn.hutool.core.util.StrUtil;
import com.mohism.pudding.kernel.model.auth.context.CommonConstant;
import com.mohism.pudding.kernel.model.exception.ServiceException;
import com.mohism.pudding.kernel.model.reqres.response.ResponseData;
import com.mohism.pudding.system.manager.core.aync.SyncAddMessage;
import com.mohism.pudding.system.manager.entity.Depart;
import com.mohism.pudding.system.manager.entity.Role;
import com.mohism.pudding.system.manager.entity.SysUser;
import com.mohism.pudding.system.manager.entity.UserRole;
import com.mohism.pudding.system.manager.modular.service.*;
import com.mohism.pudding.system.manager.vo.PageVo;
import com.mohism.pudding.system.manager.vo.SearchVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


/**
 * <p>
 *  用户管理
 * </p>
 *
 * @author real earth
 * @since 2019-07-10
 */
@Slf4j
@RestController
@Api(description = "用户接口")
@RequestMapping("/pudding/user")
@CacheConfig(cacheNames = "sysUser")
public class UserController {

    @Autowired
    private SysUserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private DepartService departmentService;

    @Autowired
    private HeaderDepartService departmentHeaderService;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private SyncAddMessage addMessage;
//
//    @Autowired
//    private ActNodeService actNodeService;

    @Autowired
    private QQService qqService;

    @Autowired
    private WeiboService weiboService;

    @Autowired
    private GithubService githubService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private SecurityService securityService;


    @RequestMapping(value = "/smsLogin", method = RequestMethod.POST)
//    @SystemLog(description = "短信登录", type = LogType.LOGIN)
    @ApiOperation(value = "短信登录接口")
    public ResponseData smsLogin(@RequestParam String mobile,
                                   @RequestParam String code,
                                   @RequestParam(required = false) Boolean saveLogin){

        // 验证短信验证码
        String v = redisTemplate.opsForValue().get(CommonConstant.PRE_SMS + mobile);
        if(StrUtil.isBlank(v)){
            throw new ServiceException(001,"验证码失效或KEY不正确");
        }
        if(!code.equals(v)){
            throw new ServiceException(002,"验证码不正确");
        }
        SysUser u = userService.findByMobile(mobile);
        if(u==null){
            throw new ServiceException(003,"手机号不存在");
        }
        String accessToken = securityService.getToken(u.getUserName(), saveLogin);
        // 已验证 清除key
        redisTemplate.delete(CommonConstant.PRE_SMS + mobile);
        return ResponseData.success(accessToken);
    }

    @RequestMapping(value = "/resetByMobile", method = RequestMethod.POST)
    @ApiOperation(value = "通过短信重置密码")
    public ResponseData resetByMobile(@RequestParam String mobile,
                                        @RequestParam String code,
                                        @RequestParam String password,
                                        @RequestParam String passStrength){

        // 验证短信验证码
        String v = redisTemplate.opsForValue().get(CommonConstant.PRE_SMS + mobile);
        if(StrUtil.isBlank(v)){
            return ResponseData.error("验证码失效或KEY不正确");
        }
        if(!code.equals(v)){
            return ResponseData.error("验证码不正确");
        }
        SysUser u = userService.findByMobile(mobile);
        String encryptPass= new BCryptPasswordEncoder().encode(password);
        u.setPassword(encryptPass);
        u.setPassStrength(passStrength);
        userService.updateById(u);
        // 删除缓存
        redisTemplate.delete("user::"+u.getUserName());
        // 已验证清除key
        redisTemplate.delete(CommonConstant.PRE_SMS + mobile);
        return ResponseData.error("重置密码成功");
    }

    /**
     * 在线demo所需 未使用短信验证码
     * @param u
     * @param verify
     * @param captchaId
     * @return
     */
    @RequestMapping(value = "/regist", method = RequestMethod.POST)
    @ApiOperation(value = "注册用户")
    public ResponseData regist(@ModelAttribute SysUser u,
                                 @RequestParam String verify,
                                 @RequestParam String captchaId){

        if(StrUtil.isBlank(verify)|| StrUtil.isBlank(u.getUserName())
                || StrUtil.isBlank(u.getPassword())){
            return ResponseData.error("缺少必需表单字段");
        }

        // 图形验证码
        String code=redisTemplate.opsForValue().get(captchaId);
        if(StrUtil.isBlank(code)){
            return ResponseData.error("验证码已过期，请重新获取");
        }

        if(!verify.toLowerCase().equals(code.toLowerCase())) {
            log.error("注册失败，验证码错误：code:"+ verify +",redisCode:"+code.toLowerCase());
            return ResponseData.error("验证码输入错误");
        }

        if(userService.findBySysUsername(u.getUserName())!=null){
            return ResponseData.error("该用户名已被注册");
        }
        // 删除缓存
        redisTemplate.delete("user::"+u.getUserName());

        if(userService.findByMobile(u.getMobile())!=null){
            return ResponseData.error("该手机号已被注册");
        }

        String encryptPass = new BCryptPasswordEncoder().encode(u.getPassword());
        u.setPassword(encryptPass);
        u.setType(CommonConstant.USER_TYPE_NORMAL);
        boolean isOk = userService.save(u);
        if(!isOk){
            return ResponseData.error("注册失败");
        }
        // 为新用户设置默认角色
        List<Role> roleList = roleService.findByDefaultRole(true);
        if(roleList!=null&&roleList.size()>0){
            for(Role role : roleList){
                UserRole ur = new UserRole();
                // ID 55
//                ur.setUserId(user.getId());
                ur.setRoleId(role.getId());
                userRoleService.save(ur);
            }
        }
        // 异步发送创建账号消息
        addMessage.addSendMessage(u.getId());

        return ResponseData.success(u);
    }

    @RequestMapping(value = "/info", method = RequestMethod.GET)
    @ApiOperation(value = "获取当前登录用户接口")
    public ResponseData getUserInfo(){

        SysUser u = securityService.getCurrSysUser();
        u.setPassword(null);
        return ResponseData.success(u);
    }

    @RequestMapping(value = "/changeMobile", method = RequestMethod.POST)
    @ApiOperation(value = "修改绑定手机")
    public ResponseData changeMobile(@RequestParam String mobile,
                                       @RequestParam String code){

        // 验证短信验证码
        String v = redisTemplate.opsForValue().get(CommonConstant.PRE_SMS + mobile);
        if(StrUtil.isBlank(v)){
            return ResponseData.error("验证码失效或KEY不正确");
        }
        if(!code.equals(v)){
            return ResponseData.error("验证码不正确");
        }
        if(userService.findByMobile(mobile)!=null){
            return ResponseData.error("该手机号已绑定其他账户");
        }
        SysUser u = securityService.getCurrSysUser();
        u.setMobile(mobile);
        userService.updateById(u);
        // 删除缓存
        redisTemplate.delete("user::"+u.getUserName());
        // 已验证清除key
        redisTemplate.delete(CommonConstant.PRE_SMS + mobile);
        return ResponseData.error("修改手机号成功");
    }

    @RequestMapping(value = "/unlock", method = RequestMethod.POST)
    @ApiOperation(value = "解锁验证密码")
    public ResponseData unLock(@RequestParam String password){

        SysUser u = securityService.getCurrSysUser();
        if(!new BCryptPasswordEncoder().matches(password, u.getPassword())){
            return ResponseData.error("密码不正确");
        }
        return ResponseData.success(null);
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ApiOperation(value = "修改用户自己资料",notes = "用户名密码不会修改 需要username更新缓存")
    @CacheEvict(key = "#u.username")
    public ResponseData editOwn(@ModelAttribute SysUser u){

        SysUser old = securityService.getCurrSysUser();
        u.setUserName(old.getUserName());
        u.setPassword(old.getPassword());
        boolean isOk = userService.updateById(u);
        if(!isOk){
            return ResponseData.error("修改失败");
        }
        return ResponseData.error("修改成功");
    }

    @RequestMapping(value = "/admin/edit", method = RequestMethod.POST)
    @ApiOperation(value = "管理员修改资料",notes = "需要通过id获取原用户信息 需要username更新缓存")
    @CacheEvict(key = "#u.userName")
    public ResponseData edit(@ModelAttribute SysUser u,
                               @RequestParam(required = false) String[] roles){

        SysUser old = userService.getById(u.getId());
        //若修改了用户名
        if(!old.getUserName().equals(u.getUserName())){
            //若修改用户名删除原用户名缓存
            redisTemplate.delete("user::"+old.getUserName());
            //判断新用户名是否存在
            if(userService.findBySysUsername(u.getUserName())!=null){
                return ResponseData.error("该用户名已存在");
            }
            //删除缓存
            redisTemplate.delete("user::"+u.getUserName());
        }

        // 若修改了手机和邮箱判断是否唯一
        if(!old.getMobile().equals(u.getMobile())&&userService.findByMobile(u.getMobile())!=null){
            return ResponseData.error("该手机号已绑定其他账户");
        }
        if(!old.getEmail().equals(u.getEmail())&&userService.findByMobile(u.getEmail())!=null){
            return ResponseData.error("该邮箱已绑定其他账户");
        }

        u.setPassword(old.getPassword());
        boolean isOk = userService.updateById(u);
        if(!isOk){
            return ResponseData.error("修改失败");
        }
        //删除该用户角色
        userRoleService.deleteBySysUserId(u.getId());
        if(roles!=null&&roles.length>0){
            //新角色
            for(String roleId : roles){
                UserRole ur = new UserRole();
                ur.setRoleId(roleId);
                ur.setUserId(u.getId());
                userRoleService.save(ur);
            }
        }
        //手动删除缓存
        redisTemplate.delete("userRole::"+u.getId());
        redisTemplate.delete("userRole::depIds:"+u.getId());
        return ResponseData.error("修改成功");
    }

    /**
     * 线上demo不允许测试账号改密码
     * @param password
     * @param newPass
     * @return
     */
    @RequestMapping(value = "/modifyPass", method = RequestMethod.POST)
    @ApiOperation(value = "修改密码")
    public ResponseData modifyPass(@ApiParam("旧密码") @RequestParam String password,
                                     @ApiParam("新密码") @RequestParam String newPass,
                                     @ApiParam("密码强度") @RequestParam String passStrength){

        SysUser user = securityService.getCurrSysUser();
        //在线DEMO所需
        if("test".equals(user.getUserName())||"test2".equals(user.getUserName())){
            return ResponseData.error("演示账号不支持修改密码");
        }

        if(!new BCryptPasswordEncoder().matches(password, user.getPassword())){
            return ResponseData.error("旧密码不正确");
        }

        String newEncryptPass= new BCryptPasswordEncoder().encode(newPass);
        user.setPassword(newEncryptPass);
        user.setPassStrength(passStrength);
        userService.updateById(user);

        //手动更新缓存
        redisTemplate.delete("user::"+user.getUserName());

        return ResponseData.error("修改密码成功");
    }

    @RequestMapping(value = "/getByCondition", method = RequestMethod.GET)
    @ApiOperation(value = "多条件分页获取用户列表")
    public ResponseData getByCondition(@ModelAttribute SysUser user,
                                             @ModelAttribute SearchVo searchVo,
                                             @ModelAttribute PageVo pageVo){

        List<SysUser> users = userService.findUserByCondition(null,user);
        for(SysUser u: users){
            // 关联部门
            if(StrUtil.isNotBlank(u.getDepartmentId())){
                Depart depart = departmentService.getById(u.getDepartmentId());
                u.setDepartmentTitle(depart.getDepartname());
            }
            // 关联角色
            List<Role> list = userRoleService.findBySysUserId(u.getId());
            u.setRoles(list);
            // 清除持久上下文环境 避免后面语句导致持久化
            
            u.setPassword(null);
        }
        return ResponseData.success(users);
    }

    @RequestMapping(value = "/getByDepartmentId/{departmentId}", method = RequestMethod.GET)
    @ApiOperation(value = "部门ID获取用户列表")
    public ResponseData getByCondition(@PathVariable String departmentId){

        List<SysUser> list = userService.findByDepartmentId(departmentId);
        
        list.forEach(u -> {
            u.setPassword(null);
        });
        return ResponseData.success(list);
    }

    @RequestMapping(value = "/searchByName/{username}", method = RequestMethod.GET)
    @ApiOperation(value = "通过用户名搜索用户")
    public ResponseData searchByName(@PathVariable String username) throws UnsupportedEncodingException {

        List<SysUser> list = userService.findBySysUsernameLikeAndStatus("%"+ URLDecoder.decode(username, "utf-8")+"%", CommonConstant.STATUS_NORMAL);
        return ResponseData.success(list);
    }

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    @ApiOperation(value = "获取全部用户数据")
    public ResponseData getByCondition(){

        List<SysUser> list = userService.list();
        for(SysUser u: list){
            // 关联部门
            if(StrUtil.isNotBlank(u.getDepartmentId())){
                Depart department = departmentService.getById(u.getDepartmentId());
                u.setDepartmentTitle(department.getDepartname());
            }
            // 清除持久上下文环境 避免后面语句导致持久化
            
            u.setPassword(null);
        }
        return ResponseData.success(list);
    }

    @RequestMapping(value = "/admin/add", method = RequestMethod.POST)
    @ApiOperation(value = "添加用户")
    public ResponseData regist(@ModelAttribute SysUser u,
                                 @RequestParam(required = false) String[] roles){

        if(StrUtil.isBlank(u.getUserName()) || StrUtil.isBlank(u.getPassword())){
            return ResponseData.error("缺少必需表单字段");
        }

        if(userService.findBySysUsername(u.getUserName())!=null){
            return ResponseData.error("该用户名已被注册");
        }
        //删除缓存
        redisTemplate.delete("user::"+u.getUserName());

        String encryptPass = new BCryptPasswordEncoder().encode(u.getPassword());
        u.setPassword(encryptPass);
        Boolean user = userService.saveOrUpdate(u);
        if(!user){
            return ResponseData.error("添加失败");
        }
        if(roles!=null&&roles.length>0){
            //添加角色
            for(String roleId : roles){
                UserRole ur = new UserRole();
                ur.setUserId(u.getId());
                ur.setRoleId(roleId);
                userRoleService.save(ur);
            }
        }
        // 发送创建账号消息  --保存用户返回ID
        addMessage.addSendMessage("id");//user.getId()

        return ResponseData.error("添加成功");
    }

    @RequestMapping(value = "/admin/disable/{userId}", method = RequestMethod.POST)
    @ApiOperation(value = "后台禁用用户")
    public ResponseData disable(@ApiParam("用户唯一id标识") @PathVariable String userId){

        SysUser user = userService.getById(userId);
        if(user==null){
            return ResponseData.error("通过userId获取用户失败");
        }
        user.setStatus(CommonConstant.USER_STATUS_LOCK);
        userService.updateById(user);
        //手动更新缓存
        redisTemplate.delete("user::"+user.getUserName());
        return ResponseData.error("操作成功");
    }

    @RequestMapping(value = "/admin/enable/{userId}", method = RequestMethod.POST)
    @ApiOperation(value = "后台启用用户")
    public ResponseData enable(@ApiParam("用户唯一id标识") @PathVariable String userId){

        SysUser user = userService.getById(userId);
        if(user==null){
            return ResponseData.error("通过userId获取用户失败");
        }
        user.setStatus(CommonConstant.USER_STATUS_NORMAL);
        userService.updateById(user);
        //手动更新缓存
        redisTemplate.delete("user::"+user.getUserName());
        return ResponseData.error("操作成功");
    }

    @RequestMapping(value = "/delByIds/{ids}", method = RequestMethod.DELETE)
    @ApiOperation(value = "批量通过ids删除")
    public ResponseData delAllByIds(@PathVariable String[] ids){

        for(String id:ids){
            SysUser u = userService.getById(id);
            //删除相关缓存
            redisTemplate.delete("user::" + u.getUserName());
            redisTemplate.delete("userRole::" + u.getId());
            redisTemplate.delete("userRole::depIds:" + u.getId());
            redisTemplate.delete("permission::userMenuList:" + u.getId());
            Set<String> keys = redisTemplate.keys("department::*");
            redisTemplate.delete(keys);

            // 删除关联社交账号
            qqService.deleteByUsername(u.getUserName());
            weiboService.deleteByUsername(u.getUserName());
            githubService.deleteByUsername(u.getUserName());

            userService.removeById(id);

            //删除关联角色
            userRoleService.deleteBySysUserId(id);
            // 删除关联部门负责人
            departmentHeaderService.deleteByUserId(id);
            // 删除流程关联节点
//            actNodeService.deleteByRelateId(id);
        }
        return ResponseData.error("批量通过id删除数据成功");
    }

    @RequestMapping(value = "/importData", method = RequestMethod.POST)
    @ApiOperation(value = "导入用户数据")
    public ResponseData importData(@RequestBody List<SysUser> users){

        List<Integer> errors = new ArrayList<>();
        List<String> reasons = new ArrayList<>();
        int count = 0;
        for(SysUser u: users){
            count++;
            // 验证用户名密码不为空
            if(StrUtil.isBlank(u.getUserName())||StrUtil.isBlank(u.getPassword())){
                errors.add(count);
                reasons.add("用户名或密码为空");
                continue;
            }
            // 验证用户名唯一
            if(userService.findBySysUsername(u.getUserName())!=null){
                errors.add(count);
                reasons.add("用户名已存在");
                continue;
            }
            //删除缓存
            redisTemplate.delete("user::"+u.getUserName());
            // 加密密码
            u.setPassword(new BCryptPasswordEncoder().encode(u.getPassword()));
            // 验证部门id正确性
            if(StrUtil.isNotBlank(u.getDepartmentId())){
                try {
                    Depart d = departmentService.getById(u.getDepartmentId());
                    log.info(d.toString());
                }catch (Exception e){
                    errors.add(count);
                    reasons.add("部门id不存在");
                    continue;
                }
            }
            if(u.getStatus()==null){
                u.setStatus(CommonConstant.USER_STATUS_NORMAL);
            }
            // 分配默认角色
            if(u.getDefaultRole()!=null&&u.getDefaultRole()==1){
                List<Role> roleList = roleService.findByDefaultRole(true);
                if(roleList!=null&&roleList.size()>0){
                    for(Role role : roleList){
                        UserRole ur = new UserRole();
                        ur.setUserId(u.getId());
                        ur.setRoleId(role.getId());
                        userRoleService.save(ur);
                    }
                }
            }
            // 保存数据
            userService.save(u);
        }
        int successCount = users.size() - errors.size();
        String successMessage = "全部导入成功，共计 " + successCount + " 条数据";
        String failMessage = "导入成功 " + successCount + " 条，失败 " + errors.size() + " 条数据。<br>" +
                "第 " + errors.toString() + " 行数据导入出错，错误原因分别为：<br>" + reasons.toString();
        String message = "";
        if(errors.size()==0){
            message = successMessage;
        }else{
            message = failMessage;
        }
        return ResponseData.error(message);
    }
}
