package com.mohism.pudding.system.manager.modular.provider;

import com.mohism.pudding.kernel.model.api.AuthService;
import com.mohism.pudding.system.manager.context.LoginUser;
import com.mohism.pudding.system.manager.modular.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 对外鉴权服务的提供者
 *
 * @author real earth
 * @date 2019-06-30-上午9:05
 */
@RestController
@Primary
public class AuthServiceProvider implements AuthService {

    @Autowired
    private SysUserService sysUserService;

    @Override
    public String login(@RequestParam("account") String account, @RequestParam("password") String password) {
//        return sysUserService.login(account, password);
        return null;
    }

    @Override
    public boolean checkToken(@RequestParam("token") String token) {
//        return sysUserService.checkToken(token);
        return false;
    }

    @Override
    public void logout(@RequestParam("token") String token) {
//        sysUserService.logout(token);
    }


    @Override
    public LoginUser getLoginUserByToken(@RequestParam("token") String token) {
//        return sysUserService.getLoginUserByToken(token);
        return null;
    }

}
