//package com.mohism.pudding.system.manager.util;
//
//import cn.hutool.core.util.StrUtil;
//import com.google.gson.Gson;
//import com.mohism.pudding.kernel.model.exception.ServiceException;
//import com.mohism.pudding.kernel.model.util.ObjectUtil;
//import com.mohism.pudding.system.manager.core.constants.SettingConstant;
//import com.mohism.pudding.system.manager.exception.ManagerExceptionEnum;
//import com.mohism.pudding.system.manager.vo.EmailSetting;
//import lombok.extern.slf4j.Slf4j;
//import org.beetl.core.TemplateEngine;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.StringRedisTemplate;
//import org.springframework.mail.javamail.JavaMailSenderImpl;
//import org.springframework.mail.javamail.MimeMessageHelper;
//import org.springframework.scheduling.annotation.Async;
//import org.springframework.stereotype.Component;
//
//import java.util.Properties;
//
///**
// * <p>
// *  邮件工具
// * </p>
// *
// * @author real earth
// * @since 2019-06-25
// */
//@Component
//@Slf4j
//public class EmailUtil {
//
//    @Autowired
//    private StringRedisTemplate redisTemplate;
//
//    @Autowired
//    private TemplateEngine templateEngine;
//
//    public EmailSetting getEmailSetting(){
//
//        String v = redisTemplate.opsForValue().get(SettingConstant.EMAIL_SETTING);
//        if(StrUtil.isBlank(v)){
//            throw new ServiceException(ManagerExceptionEnum.NO_SEND_EMAIL);
//        }
//        return new Gson().fromJson(v, EmailSetting.class);
//    }
//
//    /**
//     * 异步发送邮件
//     * @param sendTo 发送者
//     * @param title 邮件标题
//     * @param templateName 邮件模板
//     * @param o 模板替换对象
//     */
//    @Async
//    public void sendTemplateEmail(String sendTo, String title, String templateName, Object o){
//
//        EmailSetting es = getEmailSetting();
//
//        JavaMailSenderImpl senderImpl = new JavaMailSenderImpl();
//
//        //设定邮箱服务器配置
//        senderImpl.setHost(es.getHost());
//        senderImpl.setUsername(es.getUsername());
//        senderImpl.setPassword(es.getPassword());
//        Properties prop = new Properties();
//
//        //服务器进行认证
//        prop.put("mail.smtp.auth", "true");
//        prop.put("mail.smtp.timeout", "20000");
//        //邮箱发送服务器端口,这里设置为465端口
//        prop.setProperty("mail.smtp.port", "465");
//        prop.setProperty("mail.smtp.socketFactory.port", "465");
//        prop.put("mail.smtp.ssl.enable", "true");
//        senderImpl.setJavaMailProperties(prop);
//
//        //发送html邮件
//        MimeMessage mailMessage = senderImpl.createMimeMessage();
//        MimeMessageHelper messageHelper = null;
//        //设置邮件内容
//        try {
//            messageHelper = new MimeMessageHelper(mailMessage,true,"utf-8");
//
//            messageHelper.setTo(sendTo);
//            messageHelper.setFrom(es.getUsername());
//            messageHelper.setSubject(title);
//            Context context = new Context();
//            context.setVariables(ObjectUtil.beanToMap(o));
//            // 获取模板html代码
//            String content = templateEngine.process(templateName, context);
//            // true表示HTML格式的邮件
//            messageHelper.setText(content, true);
//            // 发送邮件
//            senderImpl.send(mailMessage);
//        } catch (Exception e) {
//            log.error(e.toString());
//            throw new XbootException("发送邮件失败，请检查邮件配置");
//        }
//    }
//}
