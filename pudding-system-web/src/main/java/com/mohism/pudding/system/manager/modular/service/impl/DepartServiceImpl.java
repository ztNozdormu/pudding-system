package com.mohism.pudding.system.manager.modular.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mohism.pudding.system.manager.entity.Depart;
import com.mohism.pudding.system.manager.modular.mapper.DepartMapper;
import com.mohism.pudding.system.manager.modular.service.DepartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author real earth
 * @since 2019-06-25
 */
@Slf4j
@Service
@Transactional
public class DepartServiceImpl extends ServiceImpl<DepartMapper, Depart> implements DepartService {

}
