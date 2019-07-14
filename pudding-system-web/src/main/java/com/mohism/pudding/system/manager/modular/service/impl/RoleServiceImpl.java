package com.mohism.pudding.system.manager.modular.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mohism.pudding.system.manager.entity.Role;
import com.mohism.pudding.system.manager.modular.mapper.RoleMapper;
import com.mohism.pudding.system.manager.modular.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 角色接口实现
 * @author Exrickx
 */
@Slf4j
@Service
@Transactional
public class RoleServiceImpl extends ServiceImpl<RoleMapper,Role> implements RoleService {


    @Override
    public List<Role> findByDefaultRole(Boolean defaultRole) {

        return this.list(new QueryWrapper<Role>().eq("defaultRole",defaultRole));
    }
}
