package com.mohism.pudding.system.manager.modular.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mohism.pudding.system.manager.entity.MessageSend;
import org.springframework.data.domain.Pageable;

/**
 * <p>
 *  消息发送业务接口层
 * </p>
 *
 * @author real earth
 * @since 2019-07-10
 */
public interface MessageSendService extends IService<MessageSend> {

    /**
     * 发送消息 带websock推送
     * @param messageSend
     * @return
     */
    MessageSend send(MessageSend messageSend);

    /**
     * 通过消息id删除
     * @param messageId
     */
    boolean deleteByMessageId(String messageId);

    /**
     * 多条件分页获取
     * @param messageSend
     * @param pageable
     * @return
     */
    Page<MessageSend> findByCondition(MessageSend messageSend, Pageable pageable);
}