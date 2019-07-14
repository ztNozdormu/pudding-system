package com.mohism.pudding.system.manager.modular.service.impl;


import cn.hutool.core.util.StrUtil;
import com.google.gson.Gson;
import com.mohism.pudding.kernel.jwt.utils.JwtTokenUtil;
import com.mohism.pudding.kernel.model.auth.context.CommonConstant;
import com.mohism.pudding.system.manager.core.constants.SecurityConstant;
import com.mohism.pudding.system.manager.entity.Permission;
import com.mohism.pudding.system.manager.entity.Role;
import com.mohism.pudding.system.manager.entity.SysUser;
import com.mohism.pudding.system.manager.modular.service.SecurityService;
import com.mohism.pudding.system.manager.modular.service.SysUserService;
import com.mohism.pudding.system.manager.modular.service.UserRoleService;
import com.mohism.pudding.system.manager.vo.TokenUser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 *  security 业务实现类
 * </p>
 *
 * @author real earth
 * @since 2019-07-08
 */
@Slf4j
@Service
@Transactional
public class SecurityServiceImpl implements SecurityService {

    @Value("${pudding.token.redis}")
    private Boolean tokenRedis;

    @Value("${pudding.saveLoginTime}")
    private Integer saveLoginTime;

    @Value("${pudding.tokenExpireTime}")
    private Integer tokenExpireTime;

    @Value("${pudding.token.storePerms}")
    private Boolean storePerms;

    @Autowired
    private SysUserService userService;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private StringRedisTemplate redisTemplate;

    // 处理登录成功业务流程()
    public String getToken(String username, Boolean saveLogin){

        Boolean saved = false;
        // 默认saveLogin=true
        if(saveLogin==null||saveLogin){
            saved = true;
            // 如果token不保存到redis，那么用jwt进行交互
            if(!tokenRedis){
                // 设置jwt过期时间
                tokenExpireTime = saveLoginTime * 60 * 24;
            }
        }
        // 生成token
        SysUser u = userService.findBySysUsername(username);
        List<String> list = new ArrayList<>();
        // 是否缓存权限到token中
        if(storePerms){
            for(Permission p : u.getPermissions()){
                if(CommonConstant.PERMISSION_OPERATION.equals(p.getType())
                        && StrUtil.isNotBlank(p.getTitle())
                        && StrUtil.isNotBlank(p.getPath())) {
                    list.add(p.getTitle());
                }
            }
            for(Role r : u.getRoles()){
                list.add(r.getRoleName());
            }
        }
        // 登陆成功生成token
        String token;
        // 如果设置将token保存到redis中
        if(tokenRedis){
            // redis
            token = UUID.randomUUID().toString().replace("-", "");
            // 用户姓名 权限信息 是否保存标记
            TokenUser user = new TokenUser(u.getUserName(), list, saved);
            // 单点登录 之前的token失效
            String oldToken = redisTemplate.opsForValue().get(SecurityConstant.USER_TOKEN + u.getUserName());
            if(StrUtil.isNotBlank(oldToken)){
                redisTemplate.delete(SecurityConstant.TOKEN_PRE + oldToken);
            }
            // 保存登录信息--用户选择保存登录信息则默认保存7天时间(默认选项)，不选择保存则默认保存60分钟
            if(saved){
                redisTemplate.opsForValue().set(SecurityConstant.USER_TOKEN + u.getUserName(), token, saveLoginTime, TimeUnit.DAYS);
                redisTemplate.opsForValue().set(SecurityConstant.TOKEN_PRE + token, new Gson().toJson(user), saveLoginTime, TimeUnit.DAYS);
            }else{
                redisTemplate.opsForValue().set(SecurityConstant.USER_TOKEN + u.getUserName(), token, tokenExpireTime, TimeUnit.MINUTES);
                redisTemplate.opsForValue().set(SecurityConstant.TOKEN_PRE + token, new Gson().toJson(user), tokenExpireTime, TimeUnit.MINUTES);
            }
        }else{
            // 否则用jwt
            token = SecurityConstant.TOKEN_SPLIT + Jwts.builder()
                    //主题 放入用户名
                    .setSubject(u.getUserName())
                    //自定义属性 放入用户拥有请求权限
                    .claim(SecurityConstant.AUTHORITIES, new Gson().toJson(list))
                    //失效时间
                    .setExpiration(new Date(System.currentTimeMillis() + tokenExpireTime * 60 * 1000))
                    //签名算法和密钥
                    .signWith(SignatureAlgorithm.HS512, SecurityConstant.JWT_SIGN_KEY)
                    .compact();
        }
        return token;
    }

    /**
     * 获取当前登录用户
     * @return
     */
    public SysUser getCurrSysUser(){

        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userService.findBySysUsername(user.getUsername());
    }

    /**
     * 获取当前用户数据权限 null代表具有所有权限
     */
    public List<String> getDeparmentIds(){

        List<String> deparmentIds = new ArrayList<>();
        SysUser u = getCurrSysUser();
        // 用户角色
        List<Role> userRoleList = userRoleService.findBySysUserId(u.getId());
        // 判断有无全部数据的角色
        Boolean flagAll = false;
        for(Role r : userRoleList){
            if(r.getDataType()==null||r.getDataType().equals(CommonConstant.DATA_TYPE_ALL)){
                flagAll = true;
                break;
            }
        }
        if(flagAll){
            return null;
        }
        // 查找自定义
        return userRoleService.findDepIdsByUserId(u.getId());
    }

    /**
     * 通过用户名获取用户拥有权限
     * @param username
     */
    public List<GrantedAuthority> getCurrUserPerms(String username){

        List<GrantedAuthority> authorities = new ArrayList<>();
        for(Permission p : userService.findBySysUsername(username).getPermissions()){
            authorities.add(new SimpleGrantedAuthority(p.getTitle()));
        }
        return authorities;
    }
}
