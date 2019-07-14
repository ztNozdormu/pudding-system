package com.mohism.pudding.system.manager.modular.service;



import com.baomidou.mybatisplus.extension.service.IService;
import com.mohism.pudding.system.manager.entity.RoleDepart;
import com.mohism.pudding.system.manager.entity.RolePermission;

import java.util.List;

/**
 * <p>
 *   角色权限 业务接口层
 * </p>
 *
 * @author real earth
 * @since 2019-07-10
 */
public interface RolePermissionService extends IService<RolePermission> {

    /**
     * 通过permissionId获取
     * @param permissionId
     * @return
     */
    List<RolePermission> findByPermissionId(String permissionId);

    /**
     * 通过roleId获取
     * @param roleId
     * @return
     */
    List<RolePermission> findByRoleId(String roleId);

    /**
     * 通过roleId删除
     * @param roleId
     */
    boolean deleteByRoleId(String roleId);
}