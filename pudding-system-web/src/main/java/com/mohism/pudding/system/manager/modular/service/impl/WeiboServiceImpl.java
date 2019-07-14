package com.mohism.pudding.system.manager.modular.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mohism.pudding.system.manager.entity.social.Weibo;
import com.mohism.pudding.system.manager.modular.mapper.WeiboMapper;
import com.mohism.pudding.system.manager.modular.service.WeiboService;
import com.mohism.pudding.system.manager.vo.SearchVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 微博登录 业务实现类
 * </p>
 *
 * @author real earth
 * @since 2019-06-30
 */
@Slf4j
@Service
@Transactional
public class WeiboServiceImpl extends ServiceImpl<WeiboMapper,Weibo> implements WeiboService {


    @Override
    public Weibo findByOpenId(String openId) {

        return this.baseMapper.selectOne(new QueryWrapper<Weibo>().eq("openId",openId));
}

    @Override
    public Weibo findByRelateUsername(String username) {

        return this.baseMapper.selectOne(new QueryWrapper<Weibo>().eq("username",username));
    }

    @Override
    public Page<Weibo> findByCondition(String username, String relateUsername, SearchVo searchVo, Pageable pageable) {

//        return weiboDao.findAll(new Specification<Weibo>() {
//            @Nullable
//            @Override
//            public Predicate toPredicate(Root<Weibo> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
//
//                Path<String> usernameField = root.get("username");
//                Path<String> relateUsernameField = root.get("relateUsername");
//                Path<Date> createTimeField=root.get("createTime");
//
//                List<Predicate> list = new ArrayList<Predicate>();
//
//                if(StrUtil.isNotBlank(username)){
//                    list.add(cb.like(usernameField,'%'+ username + '%'));
//                }
//                if(StrUtil.isNotBlank(relateUsername)){
//                    list.add(cb.like(relateUsernameField,'%'+ relateUsername + '%'));
//                }
//                //创建时间
//                if(StrUtil.isNotBlank(searchVo.getStartDate())&&StrUtil.isNotBlank(searchVo.getEndDate())){
//                    Date start = DateUtil.parse(searchVo.getStartDate());
//                    Date end = DateUtil.parse(searchVo.getEndDate());
//                    list.add(cb.between(createTimeField, start, DateUtil.endOfDay(end)));
//                }
//
//                Predicate[] arr = new Predicate[list.size()];
//                cq.where(list.toArray(arr));
//                return null;
//            }
//        }, pageable);
        return null;
    }

    @Override
    public boolean deleteByUsername(String username) {

        return this.remove(new QueryWrapper<Weibo>().eq("username",username));
    }
}