package com.mohism.pudding.system.manager.modular.controller;


import com.mohism.pudding.kernel.model.reqres.response.ResponseData;
import com.mohism.pudding.system.manager.modular.service.DepartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author real earth
 * @since 2019-06-25
 */
@Slf4j
//@Api(description = "SysDepart管理接口")
@RestController
@RequestMapping("/manager/depart")
public class DepartController {
    @Autowired
    private DepartService departService;

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
//    @ApiOperation(value = "通过id获取")
    public ResponseData get(@PathVariable String id) {

        return ResponseData.success();
    }

}