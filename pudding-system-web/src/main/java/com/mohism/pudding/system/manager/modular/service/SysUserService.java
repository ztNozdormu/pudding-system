package com.mohism.pudding.system.manager.modular.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mohism.pudding.base.dict.api.model.DictInfo;
import com.mohism.pudding.system.manager.entity.Depart;
import com.mohism.pudding.system.manager.entity.SysUser;
import com.mohism.pudding.system.manager.vo.SearchVo;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;


/**
 * <p>
 *  用户业务接口层
 * </p>
 *
 * @author real earth
 * @since 2019-07-06
 */
//@CacheConfig(cacheNames = "sysSysUser")
public interface SysUserService extends IService<SysUser> {

    /**
     * 通过用户名获取用户
     * @param SysUsername
     * @return
     */
    @Cacheable(key = "#SysUsername")
    SysUser findBySysUsername(String SysUsername);

    /**
     * 通过手机获取用户
     * @param mobile
     * @return
     */
    SysUser findByMobile(String mobile);

    /**
     * 通过邮件和状态获取用户
     * @param email
     * @return
     */
    SysUser findByEmail(String email);

    /**
     * 多条件分页获取用户
     * @param page
     * @param sysUser
     * @return
     */
     List<SysUser>  findUserByCondition(Page page, SysUser sysUser);

    /**
     * 通过部门id获取
     * @param departmentId
     * @return
     */
    List<SysUser> findByDepartmentId(String departmentId);

    /**
     * 通过用户名模糊搜索
     * @param SysUsername
     * @param status
     * @return
     */
    List<SysUser> findBySysUsernameLikeAndStatus(String SysUsername, Integer status);
}
