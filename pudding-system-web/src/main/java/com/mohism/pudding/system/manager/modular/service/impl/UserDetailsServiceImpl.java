package com.mohism.pudding.system.manager.modular.service.impl;

import cn.hutool.core.util.StrUtil;
import com.mohism.pudding.kernel.model.exception.ServiceException;
import com.mohism.pudding.system.manager.entity.SysUser;
import com.mohism.pudding.system.manager.entity.security.SecurityUserDetails;
import com.mohism.pudding.system.manager.modular.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * <p>
 *  security 用户详细相关 业务实现层
 * </p>
 *
 * @author real earth
 * @since 2019-06-25
 */
@Slf4j
@Service
public class UserDetailsServiceImpl implements UserDetailsService{

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private SysUserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{

        String flagKey = "loginFailFlag:" + username;
        String value = redisTemplate.opsForValue().get(flagKey);
        Long timeRest = redisTemplate.getExpire(flagKey, TimeUnit.MINUTES);
        if(StrUtil.isNotBlank(value)){
            //超过限制次数
            throw new ServiceException(2188,"登录错误次数超过限制，请"+timeRest+"分钟后再试");

        }
        SysUser user = userService.findBySysUsername(username);
        return new SecurityUserDetails(user);
    }
}
