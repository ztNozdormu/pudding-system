package com.mohism.pudding.system.manager.modular.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mohism.pudding.system.manager.entity.HeaderDepart;
import com.mohism.pudding.system.manager.modular.mapper.HeaderDepartMapper;
import com.mohism.pudding.system.manager.modular.service.HeaderDepartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


/**
 * <p>
 *  组织机构负责人业务实现类
 * </p>
 *
 * @author real earth
 * @since 2019-06-25
 */
@Slf4j
@Service
@Transactional
public class HeaderDepartServiceImpl  extends ServiceImpl<HeaderDepartMapper, HeaderDepart> implements HeaderDepartService {

    @Override
    public List<String> findHeaderByDepartId(String departId, Integer type) {

        List<String> list = new ArrayList<>();

        List<HeaderDepart> headers = this.list(new QueryWrapper<HeaderDepart>().eq("departId",departId).eq("type",type));
        headers.forEach(e->{
            list.add(e.getUserId());
        });
        return list;
    }

    @Override
    public List<HeaderDepart> findByDepartIdIn(List<String> departIds) {

        return  this.list(new QueryWrapper<HeaderDepart>().in("departId",departIds));
    }

    @Override
    public boolean deleteByDepartId(String departId) {

        return this.remove(new QueryWrapper<HeaderDepart>().eq("departId",departId));
    }

    @Override
    public boolean deleteByUserId(String userId) {

        return this.remove(new QueryWrapper<HeaderDepart>().eq("userId",userId));
    }
}
