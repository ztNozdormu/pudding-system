package com.mohism.pudding.system.manager.modular.service.impl;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mohism.pudding.system.manager.entity.MessageSend;
import com.mohism.pudding.system.manager.modular.mapper.MessageSendMapper;
import com.mohism.pudding.system.manager.modular.service.MessageSendService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  消息发送业务实现类
 * </p>
 *
 * @author real earth
 * @since 2019-07-10
 */
@Slf4j
@Service
@Transactional
public class MessageSendServiceImpl extends ServiceImpl<MessageSendMapper, MessageSend> implements MessageSendService {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Override
    public MessageSend send(MessageSend messageSend) {

        boolean ms = this.save(messageSend);
        messagingTemplate.convertAndSendToUser(messageSend.getUserId(),"/queue/subscribe", "您收到了新的消息");
        return messageSend;
    }

    @Override
    public boolean deleteByMessageId(String messageId) {

        return this.removeById(messageId);
    }

    public Page<MessageSend> findByCondition(MessageSend messageSend, Pageable pageable){
        return null;
    }

}