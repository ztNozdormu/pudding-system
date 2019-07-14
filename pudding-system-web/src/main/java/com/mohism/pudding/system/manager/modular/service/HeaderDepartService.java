package com.mohism.pudding.system.manager.modular.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mohism.pudding.system.manager.entity.HeaderDepart;

import java.util.List;

public interface HeaderDepartService extends IService<HeaderDepart> {

    /**
     * 通过部门和负责人类型获取
     * @param departId
     * @param type
     * @return
     */
    List<String> findHeaderByDepartId(String departId, Integer type);

    /**
     * 通过部门获取
     * @param departIds
     * @return
     */
    List<HeaderDepart> findByDepartIdIn(List<String> departIds);

    /**
     * 通过部门id删除
     * @param departId
     */
    boolean deleteByDepartId(String departId);

    /**
     * 通过userId删除
     * @param userId
     */
    boolean deleteByUserId(String userId);
}
