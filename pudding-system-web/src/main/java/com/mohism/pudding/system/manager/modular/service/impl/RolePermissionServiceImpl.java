package com.mohism.pudding.system.manager.modular.service.impl;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mohism.pudding.system.manager.entity.RolePermission;
import com.mohism.pudding.system.manager.modular.mapper.RolePermissionMapper;
import com.mohism.pudding.system.manager.modular.service.RolePermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * <p>
 *   角色权限 业务实现
 * </p>
 *
 * @author real earth
 * @since 2019-07-10
 */
@Slf4j
@Service
@Transactional
public class RolePermissionServiceImpl extends ServiceImpl<RolePermissionMapper,RolePermission> implements RolePermissionService {


    @Override
    public List<RolePermission> findByPermissionId(String permissionId) {

        return this.list(new QueryWrapper<RolePermission>().eq("permissionId",permissionId));
    }

    @Override
    public List<RolePermission> findByRoleId(String roleId) {

        return this.list(new QueryWrapper<RolePermission>().eq("roleId",roleId));
    }

    @Override
    public boolean deleteByRoleId(String roleId) {

      return this.remove(new QueryWrapper<RolePermission>().eq("roleId",roleId));
    }
}