package com.mohism.pudding.system.manager.modular.service;



import com.baomidou.mybatisplus.extension.service.IService;
import com.mohism.pudding.system.manager.entity.RoleDepart;

import java.util.List;

/**
 * <p>
 *   角色部门关联 业务接口层
 * </p>
 *
 * @author real earth
 * @since 2019-07-07
 */
public interface RoleDepartService extends IService<RoleDepart> {

    /**
     * 通过roleId获取
     * @param roleId
     * @return
     */
    List<RoleDepart> findByRoleId(String roleId);

    /**
     * 通过角色id删除
     * @param roleId
     */
    boolean deleteByRoleId(String roleId);

    /**
     * 通过组织机构id删除
     * @param departId
     */
    boolean deleteByDepartId(String departId);


}