package com.mohism.pudding.system.manager.modular.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mohism.pudding.system.manager.entity.Role;
import com.mohism.pudding.system.manager.entity.SysUser;
import com.mohism.pudding.system.manager.entity.UserRole;

import java.util.List;

/**
 * <p>
 *  用户角色关联业务层
 * </p>
 *
 * @author real earth
 * @since 2019-07-06
 */
public interface UserRoleService extends IService<UserRole> {
    /**
     * 通过roleId查找
     * @param roleId
     * @return
     */
    List<UserRole> findByRoleId(String roleId);

    /**
     * 通过roleId查找用户
     * @param roleId
     * @return
     */
    List<SysUser> findSysUserByRoleId(String roleId);

    /**
     * 通过sysUserId查找角色集
     * @param sysUserId
     * @return
     */
    List<Role> findBySysUserId(String sysUserId);

    /**
     * 删除用户角色
     * @param userId
     */
    boolean deleteBySysUserId(String userId);

    /**
     * 通过用户id获取用户角色关联的部门数据
     * @param userId
     * @return
     */
    List<String> findDepIdsByUserId(String userId);
}
