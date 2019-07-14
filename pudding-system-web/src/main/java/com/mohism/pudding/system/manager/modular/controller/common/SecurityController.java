package com.mohism.pudding.system.manager.modular.controller.common;


import com.mohism.pudding.kernel.model.reqres.response.ResponseData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  springSecurity 控制器
 * </p>
 *
 * @author real earth
 * @since 2019-06-25
 */
@Slf4j
@RestController
@Api(description = "Security相关接口")
@RequestMapping("/pudding/common")
@Transactional
public class SecurityController {

    @RequestMapping(value = "/needLogin", method = RequestMethod.GET)
    @ApiOperation(value = "没有登录")
    public ResponseData needLogin(){

        return ResponseData.error(401, "您还未登录");
    }
}
