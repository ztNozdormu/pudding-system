package com.mohism.pudding.system.manager.modular.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.mohism.pudding.system.manager.entity.Message;
import com.mohism.pudding.system.manager.vo.SearchVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * <p>
 *  消息内容业务接口层
 * </p>
 *
 * @author real earth
 * @since 2019-07-10
 */
public interface MessageService extends IService<Message> {

    /**
     * 多条件分页获取
     * @param message
     * @param searchVo
     * @param pageable
     * @return
     */
    Page<Message> findByCondition(Message message, SearchVo searchVo, Pageable pageable);

    /**
     * 通过创建发送标识获取
     * @param createSend
     * @return
     */
    List<Message> findByCreateSend(Boolean createSend);
}