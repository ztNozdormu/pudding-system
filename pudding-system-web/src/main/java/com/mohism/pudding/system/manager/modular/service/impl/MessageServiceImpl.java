package com.mohism.pudding.system.manager.modular.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mohism.pudding.system.manager.entity.Message;
import com.mohism.pudding.system.manager.modular.mapper.MessageMapper;
import com.mohism.pudding.system.manager.modular.service.MessageService;
import com.mohism.pudding.system.manager.vo.SearchVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * <p>
 *  消息内容业务实现类
 * </p>
 *
 * @author real earth
 * @since 2019-07-10
 */
@Slf4j
@Service
@Transactional
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message>  implements MessageService {


    @Override
    public Page<Message> findByCondition(Message message, SearchVo searchVo, Pageable pageable) {

//        return messageDao.findAll(new Specification<Message>() {
//            @Nullable
//            @Override
//            public Predicate toPredicate(Root<Message> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
//
//                Path<String> titleField = root.get("title");
//                Path<String> contentField = root.get("content");
//                Path<Integer> typeField = root.get("type");
//                Path<Date> createTimeField = root.get("createTime");
//
//                List<Predicate> list = new ArrayList<Predicate>();
//
//                //模糊搜素
//                if(StrUtil.isNotBlank(message.getTitle())){
//                    list.add(cb.like(titleField,'%'+message.getTitle()+'%'));
//                }
//                if(StrUtil.isNotBlank(message.getContent())){
//                    list.add(cb.like(contentField,'%'+message.getContent()+'%'));
//                }
//
//                if(message.getType()!=null){
//                    list.add(cb.equal(typeField, message.getType()));
//                }
//
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
    public List<Message> findByCreateSend(Boolean createSend) {

        return this.list(new QueryWrapper<Message>().eq("createSend",createSend));
    }
}