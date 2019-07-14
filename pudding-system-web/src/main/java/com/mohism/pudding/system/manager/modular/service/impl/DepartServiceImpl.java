package com.mohism.pudding.system.manager.modular.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mohism.pudding.system.manager.entity.Depart;
import com.mohism.pudding.system.manager.modular.mapper.DepartMapper;
import com.mohism.pudding.system.manager.modular.service.DepartService;
import com.mohism.pudding.system.manager.modular.service.SecurityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * <p>
 *  组织机构业务实现类
 * </p>
 *
 * @author real earth
 * @since 2019-06-25
 */
@Slf4j
@Service
@Transactional
public class DepartServiceImpl extends ServiceImpl<DepartMapper, Depart> implements DepartService {

    @Autowired
    private SecurityService securityService;

    @Override
    public List<Depart> findByParentIdOrderBySortOrder(String parentId, Boolean openDataFilter) {

        // 数据权限
        List<String> depIds = securityService.getDeparmentIds();
        if(depIds!=null&&depIds.size()>0&&openDataFilter){
            this.list(new QueryWrapper<Depart>().eq("parentId",parentId).in("id",depIds).orderByAsc("sortOrder"));
        }
        return this.list(new QueryWrapper<Depart>().eq("parentId",parentId).orderByAsc("sortOrder"));

    }

    @Override
    public List<Depart> findByParentIdAndStatusOrderBySortOrder(String parentId, Integer status) {

        return  this.list(new QueryWrapper<Depart>().eq("parentId",parentId).eq("status",status).orderByAsc("sortOrder"));

    }
    @Override
    public List<Depart> findByDepartNameLikeOrderBySortOrder(String departname, Boolean openDataFilter) {
        // 数据权限
        List<String> depIds = securityService.getDeparmentIds();
        if(depIds!=null&&depIds.size()>0&&openDataFilter){
            return  this.list(new QueryWrapper<Depart>().like("departname",departname).in("id",depIds).orderByAsc("sortOrder"));

        }
        return  this.list(new QueryWrapper<Depart>().like("departname",departname).orderByAsc("sortOrder"));
    }
}
