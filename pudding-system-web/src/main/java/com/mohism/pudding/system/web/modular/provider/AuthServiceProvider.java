/**
 * Copyright 2018-2020 stylefeng & fengshuonan (sn93@qq.com)
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mohism.pudding.system.web.modular.provider;

import com.mohism.pudding.kernel.model.api.AuthService;
import com.mohism.pudding.system.api.context.LoginUser;
import com.mohism.pudding.system.web.modular.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 鉴权服务的提供者
 *
 * @author fengshuonan
 * @date 2018-08-06-上午9:05
 */
@RestController
@Primary
public class AuthServiceProvider implements AuthService {

    @Autowired
    private SysUserService sysUserService;

    @Override
    public String login(@RequestParam("account") String account, @RequestParam("password") String password) {
        return sysUserService.login(account, password);
    }

    @Override
    public boolean checkToken(@RequestParam("token") String token) {
        return sysUserService.checkToken(token);
    }

    @Override
    public void logout(@RequestParam("token") String token) {
        sysUserService.logout(token);
    }

    @Override
    public LoginUser getLoginUserByToken(@RequestParam("token") String token) {
        return sysUserService.getLoginUserByToken(token);
    }
}
