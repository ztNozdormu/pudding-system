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
package com.mohism.pudding.system.web.modular.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mohism.pudding.core.util.ToolUtil;
import com.mohism.pudding.kernel.jwt.utils.JwtTokenUtil;
import com.mohism.pudding.kernel.model.exception.ServiceException;
import com.mohism.pudding.system.api.context.LoginUser;
import com.mohism.pudding.system.api.exception.enums.AuthExceptionEnum;
import com.mohism.pudding.system.web.core.constants.SystemConstants;
import com.mohism.pudding.system.web.modular.entity.SysUser;
import com.mohism.pudding.system.web.modular.mapper.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author stylefeng
 * @since 2018-08-26
 */
@Service
public class SysUserService extends ServiceImpl<SysUserMapper, SysUser> {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 用户登录，登录成功返回token
     *
     * @author fengshuonan
     * @Date 2018/8/26 下午3:14
     */
    public String login(String username, String password) {

        //查询账号是否存在
        SysUser sysUser = null;
        List<SysUser> accounts = this.list(new QueryWrapper<SysUser>().eq("ACCOUNT", username));
        if (accounts != null && accounts.size() > 0) {
            sysUser = accounts.get(0);
        } else {
            throw new ServiceException(AuthExceptionEnum.USER_NOT_FOUND);
        }

        //校验账号密码是否正确
        String md5Hex = ToolUtil.md5Hex(password + sysUser.getSalt());
        if (!md5Hex.equals(sysUser.getPassword())) {
            throw new ServiceException(AuthExceptionEnum.INVALID_PWD);
        }

        //生成token
        String jwtToken = jwtTokenUtil.generateToken(sysUser.getUserId().toString(), null);

        //token放入缓存
        LoginUser loginUser = new LoginUser();
        loginUser.setAccountId(sysUser.getUserId());
        BoundValueOperations<String, Object> opts = redisTemplate.boundValueOps(SystemConstants.LOGIN_USER_CACHE_PREFIX + jwtToken);
        opts.set(loginUser, SystemConstants.DEFAULT_LOGIN_TIME_OUT_SECS, TimeUnit.SECONDS);

        return jwtToken;
    }

    /**
     * 校验token是否正确
     *
     * @author fengshuonan
     * @Date 2018/8/26 下午4:06
     */
    public boolean checkToken(String token) {

        //先校验jwt是否正确
        if (!jwtTokenUtil.checkToken(token)) {
            return false;
        }

        //校验缓存是否有token
        BoundValueOperations<String, Object> opts = redisTemplate.boundValueOps(SystemConstants.LOGIN_USER_CACHE_PREFIX + token);
        LoginUser loginUser = (LoginUser) opts.get();
        if (loginUser == null) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 退出登录
     *
     * @author fengshuonan
     * @Date 2018/8/26 下午4:09
     */
    public void logout(String token) {
        redisTemplate.delete(SystemConstants.LOGIN_USER_CACHE_PREFIX + token);
    }

    /**
     * 获取登录用户通过token
     *
     * @author fengshuonan
     * @Date 2018/8/26 下午4:12
     */
    public LoginUser getLoginUserByToken(String token) {
        BoundValueOperations<String, Object> opts = redisTemplate.boundValueOps(SystemConstants.LOGIN_USER_CACHE_PREFIX + token);
        Object loginUser = opts.get();
        if (loginUser != null) {
            return (LoginUser) loginUser;
        } else {
            return null;
        }
    }
}
