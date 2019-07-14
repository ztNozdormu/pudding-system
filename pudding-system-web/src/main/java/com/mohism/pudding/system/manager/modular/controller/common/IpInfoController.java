package com.mohism.pudding.system.manager.modular.controller.common;

import com.mohism.pudding.kernel.model.reqres.response.ResponseData;
import com.mohism.pudding.system.manager.util.IpInfoUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.protocol.ResponseDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  ip以及天气相关控制器
 * </p>
 *
 * @author real earth
 * @since 2019-06-25
 */
@Slf4j
@RestController
@Api(description = "IP接口")
@RequestMapping("/pudding/common/ip")
public class IpInfoController {

    @Autowired
    private IpInfoUtil ipInfoUtil;

    @RequestMapping(value = "/info", method = RequestMethod.GET)
    @ApiOperation(value = "IP及天气相关信息")
    public ResponseData upload(HttpServletRequest request) {

        String result= ipInfoUtil.getIpWeatherInfo(ipInfoUtil.getIpAddr(request));
        return ResponseData.success(result);
    }
}