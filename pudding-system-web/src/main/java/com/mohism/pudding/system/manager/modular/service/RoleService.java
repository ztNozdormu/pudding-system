package com.mohism.pudding.system.manager.modular.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.mohism.pudding.system.manager.entity.Role;
import com.mohism.pudding.system.manager.entity.RoleDepart;

import java.util.List;

/**
 * 角色接口
 * @author Exrickx
 */
public interface RoleService  extends IService<Role> {

    /**
     * 获取默认角色
     * @param defaultRole
     * @return
     */
    List<Role> findByDefaultRole(Boolean defaultRole);
}
