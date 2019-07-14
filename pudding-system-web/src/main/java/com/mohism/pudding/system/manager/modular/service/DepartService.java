package com.mohism.pudding.system.manager.modular.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mohism.pudding.system.manager.entity.Depart;

import java.util.List;

/**
 * <p>
 *  组织机构业务实现类
 * </p>
 *
 * @author real earth
 * @since 2019-06-25
 */
public interface DepartService extends IService<Depart> {

    /**
     * 通过父id获取 升序 数据权限
     * @param parentId
     * @param openDataFilter 是否开启数据权限过滤
     * @return
     */
    List<Depart> findByParentIdOrderBySortOrder(String parentId, Boolean openDataFilter);

    /**
     * 通过父id和状态获取 升序
     * @param parentId
     * @param status
     * @return
     */
    List<Depart> findByParentIdAndStatusOrderBySortOrder(String parentId, Integer status);

    /**
     * 部门名模糊搜索 升序 数据权限
     * @param departname
     * @param openDataFilter 是否开启数据权限过滤
     * @return
     */
    List<Depart> findByDepartNameLikeOrderBySortOrder(String departname,Boolean openDataFilter);
}
