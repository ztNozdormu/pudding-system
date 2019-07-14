package com.mohism.pudding.system.manager.modular.controller.manager;

import cn.hutool.core.util.PageUtil;
import com.mohism.pudding.core.reqres.response.ResponseData;
import com.mohism.pudding.kernel.model.auth.context.CommonConstant;
import com.mohism.pudding.system.manager.entity.Message;
import com.mohism.pudding.system.manager.entity.MessageSend;
import com.mohism.pudding.system.manager.entity.SysUser;
import com.mohism.pudding.system.manager.modular.service.MessageSendService;
import com.mohism.pudding.system.manager.modular.service.MessageService;
import com.mohism.pudding.system.manager.modular.service.SysUserService;
import com.mohism.pudding.system.manager.vo.PageVo;
import com.mohism.pudding.system.manager.vo.SearchVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


/**
 * <p>
 *  消息内容管理
 * </p>
 *
 * @author real earth
 * @since 2019-07-10
 */
@Slf4j
@RestController
@Api(description = "消息内容管理接口")
@RequestMapping("/pudding/message")
@Transactional
public class MessageController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private MessageSendService sendService;

    @Autowired
    private SysUserService userService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @RequestMapping(value = "/getByCondition", method = RequestMethod.GET)
    @ApiOperation(value = "多条件分页获取")
    public ResponseData getByCondition(@ModelAttribute Message message,
                                                @ModelAttribute SearchVo searchVo,
                                                @ModelAttribute PageVo pageVo){

        Page<Message> page = messageService.findByCondition(message, searchVo, null);//PageUtil.initPage(pageVo));
        return ResponseData.success(page);
    }

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "通过id获取")
    public ResponseData get(@PathVariable String id){

        Message message = messageService.getById(id);
        return ResponseData.success(message);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ApiOperation(value = "添加消息")
    public ResponseData addMessage(@ModelAttribute Message message){

        boolean m = messageService.save(message);
        // 保存消息发送表
        List<MessageSend> messageSends = new ArrayList<>();
        if(CommonConstant.MESSAGE_RANGE_ALL.equals(message.getRange())){
            // 全体
            List<SysUser> allUser = userService.list();
            allUser.forEach(u->{
                MessageSend ms = new MessageSend();
                ms.setMessageId(message.getId()); // ID 需要处理55
                ms.setUserId(u.getId());
                messageSends.add(ms);
            });
            // 推送
            messagingTemplate.convertAndSend("/topic/subscribe", "您收到了新的系统消息");
        }else{
            // 指定用户
            for(String id:message.getUserIds()){
                MessageSend ms = new MessageSend();
                ms.setMessageId(message.getId()); // ID 需要处理55
                ms.setUserId(id);
                messageSends.add(ms);
                // 指定用户
                messagingTemplate.convertAndSendToUser(id,"/queue/subscribe", "您收到了新的消息");
            }
        }
        sendService.saveOrUpdateBatch(messageSends);
        return ResponseData.success("添加成功");
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ApiOperation(value = "编辑消息")
    public ResponseData editMessage(@ModelAttribute Message message){

        boolean m = messageService.updateById(message);
        return ResponseData.success("编辑成功");
    }

    @RequestMapping(value = "/delByIds/{ids}", method = RequestMethod.DELETE)
    @ApiOperation(value = "删除消息")
    public ResponseData delMessage(@PathVariable String[] ids){

        for(String id:ids){
//            if(ActivitiConstant.MESSAGE_PASS_ID.equals(id)||ActivitiConstant.MESSAGE_BACK_ID.equals(id)||ActivitiConstant.MESSAGE_DELEGATE_ID.equals(id)
//                    ||ActivitiConstant.MESSAGE_TODO_ID.equals(id)){
//                return new ResultUtil<Object>().setErrorMsg("抱歉，无法删除工作流相关系统消息");
//            }
            messageService.removeById(id);
            // 删除发送表
            sendService.deleteByMessageId(id);
        }
        return ResponseData.success("编辑成功");
    }
}
