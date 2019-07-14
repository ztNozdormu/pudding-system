package com.mohism.pudding.system.manager.modular.controller.manager;


import cn.hutool.core.util.PageUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mohism.pudding.core.base.controller.BaseController;
import com.mohism.pudding.core.reqres.response.ResponseData;
import com.mohism.pudding.system.manager.entity.Message;
import com.mohism.pudding.system.manager.entity.MessageSend;
import com.mohism.pudding.system.manager.entity.SysUser;
import com.mohism.pudding.system.manager.modular.service.MessageSendService;
import com.mohism.pudding.system.manager.modular.service.MessageService;
import com.mohism.pudding.system.manager.modular.service.SysUserService;
import com.mohism.pudding.system.manager.vo.PageVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
 * <p>
 *  消息发送管理
 * </p>
 *
 * @author real earth
 * @since 2019-07-10
 */
@Slf4j
@RestController
@Api(description = "消息发送管理接口")
@RequestMapping("/pudding/messageSend")
@Transactional
public class MessageSendController extends BaseController {

    @Autowired
    private SysUserService userService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private MessageSendService messageSendService;


    @RequestMapping(value = "/getByCondition", method = RequestMethod.GET)
    @ApiOperation(value = "多条件分页获取")
    public ResponseData getByCondition(@ModelAttribute MessageSend ms,
                                                    @ModelAttribute PageVo pv){

        Page<MessageSend> page = messageSendService.findByCondition(ms,null);//PageUtil.initPage(pv));
        // lambda
        page.getRecords().forEach(item->{
            SysUser u = userService.getById(item.getUserId());
            item.setUsername(u.getUserName());
            Message m = messageService.getById(item.getMessageId());
            item.setTitle(m.getTitle());
            item.setContent(m.getContent());
            item.setType(m.getType());
        });
        return ResponseData.success(page);
    }
}
