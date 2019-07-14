package com.mohism.pudding.system.manager.modular.service;

import com.mohism.pudding.system.manager.entity.SysUser;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

/**
 * <p>
 *   security 业务接口层
 * </p>
 *
 * @author real earth
 * @since 2019-07-06
 */
public interface SecurityService {
    /**
     * 用户登录，登录成功返回token
     * @param username
     * @param saveLogin
     * @return
     */
    public String getToken(String username, Boolean saveLogin);

    /**
     * 获取当前登录用户
     * @return
     */
    public SysUser getCurrSysUser();

    /**
     * 获取当前用户数据权限
     * @return
     */
    public List<String> getDeparmentIds();

    /**
     * 通过用户名获取用户拥有权限
     * @param username
     * @return
     */
    List<GrantedAuthority> getCurrUserPerms(String username);

//    /**
//     *校验token是否正确
//     * @param token
//     * @return
//     */
//    public boolean checkToken(String token);
//
//    /**
//     *退出登录
//     * @param token
//     */
//    public void logout(String token);
//
//    /**
//     * 获取登录用户通过token
//     * @param token
//     * @return
//     */
//    public SysUser getLoginSysUserByToken(String token);


}
