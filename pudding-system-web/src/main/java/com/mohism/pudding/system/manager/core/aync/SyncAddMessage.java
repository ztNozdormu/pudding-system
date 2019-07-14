package com.mohism.pudding.system.manager.core.aync;


import com.mohism.pudding.system.manager.entity.Message;
import com.mohism.pudding.system.manager.entity.MessageSend;
import com.mohism.pudding.system.manager.modular.service.MessageSendService;
import com.mohism.pudding.system.manager.modular.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  异步添加消息
 * </p>
 *
 * @author real earth
 * @since 2019-07-10
 */
@Component
public class SyncAddMessage {

    @Autowired
    private MessageService messageService;

    @Autowired
    private MessageSendService messageSendService;

    @Async
    public void addSendMessage(String userId){

        // 获取需要创建账号发送的消息
        List<Message> messages = messageService.findByCreateSend(true);
        List<MessageSend> messageSends = new ArrayList<>();
        messages.forEach(item->{
            MessageSend ms = new MessageSend();
            ms.setUserId(userId);
            ms.setMessageId(item.getId());
            messageSends.add(ms);
        });
        if (messageSends.size()>0){
            messageSendService.saveOrUpdateBatch(messageSends);
        }
    }
}
