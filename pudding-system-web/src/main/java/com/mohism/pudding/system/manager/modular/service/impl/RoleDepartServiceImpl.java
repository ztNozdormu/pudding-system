package com.mohism.pudding.system.manager.modular.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mohism.pudding.system.manager.entity.RoleDepart;
import com.mohism.pudding.system.manager.modular.mapper.RoleDepartMapper;
import com.mohism.pudding.system.manager.modular.service.RoleDepartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 *  角色组织机构关联业务实现类
 * </p>
 *
 * @author real earth
 * @since 2019-07-08
 */
@Slf4j
@Service
@Transactional
public class RoleDepartServiceImpl extends ServiceImpl<RoleDepartMapper,RoleDepart> implements RoleDepartService {


    @Override
    public List<RoleDepart> findByRoleId(String roleId) {

        return this.list(new QueryWrapper<RoleDepart>().eq("roleId",roleId));
    }

    @Override
    public boolean deleteByRoleId(String roleId) {

      return this.remove(new QueryWrapper<RoleDepart>().eq("roleId",roleId));
    }

    @Override
    public boolean deleteByDepartId(String departId) {

        return this.remove(new QueryWrapper<RoleDepart>().eq("departId",departId));
    }
}