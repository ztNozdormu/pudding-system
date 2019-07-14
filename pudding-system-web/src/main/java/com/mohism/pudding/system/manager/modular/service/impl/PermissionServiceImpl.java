package com.mohism.pudding.system.manager.modular.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mohism.pudding.system.manager.entity.Permission;
import com.mohism.pudding.system.manager.modular.mapper.PermissionMapper;
import com.mohism.pudding.system.manager.modular.service.PermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 *  权限/菜单业务实现类
 * </p>
 *
 * @author real earth
 * @since 2019-06-25
 */
@Slf4j
@Service
@Transactional
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {

    @Override
    public List<Permission> findBySysUserId(String userId) {

        return this.baseMapper.findBySysUserId(userId);
    }
    @Override
    public List<Permission> findByLevelOrderBySortOrder(Integer level) {

        return this.baseMapper.selectList(new QueryWrapper<Permission>().eq("level",level).orderByAsc("sortOrder"));
    }

    @Override
    public List<Permission> findByParentIdOrderBySortOrder(String parentId) {

        return  this.baseMapper.selectList(new QueryWrapper<Permission>().eq("parentId",parentId).orderByAsc("sortOrder"));
    }

    @Override
    public List<Permission> findByTypeAndStatusOrderBySortOrder(Integer type, Integer status) {

        return this.baseMapper.selectList(new QueryWrapper<Permission>().eq("type",type).eq("status",status).orderByAsc("sort_order"));
    }

    @Override
    public List<Permission> findByTitle(String title) {

        return this.baseMapper.selectList(new QueryWrapper<Permission>().eq("title",title));
    }

    @Override
    public List<Permission> findByTitleLikeOrderBySortOrder(String title) {

        return this.baseMapper.selectList(new QueryWrapper<Permission>().like("title",title).orderByAsc("sortOrder"));
    }
}
