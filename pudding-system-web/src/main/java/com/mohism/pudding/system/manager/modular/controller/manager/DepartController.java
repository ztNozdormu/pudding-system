package com.mohism.pudding.system.manager.modular.controller.manager;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.mohism.pudding.kernel.model.auth.context.CommonConstant;
import com.mohism.pudding.kernel.model.reqres.response.ResponseData;
import com.mohism.pudding.system.manager.entity.Depart;
import com.mohism.pudding.system.manager.entity.HeaderDepart;
import com.mohism.pudding.system.manager.entity.SysUser;
import com.mohism.pudding.system.manager.modular.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * <p>
 *  组织机构管理
 * </p>
 *
 * @author real earth
 * @since 2019-06-25
 */
@Slf4j
@Api(description = "组织机构管理接口")
@RestController
@RequestMapping("/pudding/depart")
public class DepartController {
    @Autowired
    private DepartService departService;

    @Autowired
    private SysUserService userService;

    @Autowired
    private RoleDepartService roleDepartService;

    @Autowired
    private HeaderDepartService headerDepartService;

//    @Autowired
//    private ActNodeService actNodeService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private SecurityService securityService;

    @RequestMapping(value = "/getByParentId/{parentId}", method = RequestMethod.GET)
    @ApiOperation(value = "通过parentId获取")
    public ResponseData getByParentId(@PathVariable String parentId,
                                                  @ApiParam("是否开始数据权限过滤") @RequestParam(required = false, defaultValue = "true") Boolean openDataFilter){
        List<Depart> list = new ArrayList<>();
        SysUser u = securityService.getCurrSysUser();
        String key = "Depart::"+parentId+":"+u.getId()+"_"+openDataFilter;
        // 先从缓存中查询
        String v = redisTemplate.opsForValue().get(key);
        if(StrUtil.isNotBlank(v)){
            list = new Gson().fromJson(v, new TypeToken<List<Depart>>(){}.getType());
            return ResponseData.success(list);
        }
        list = departService.findByParentIdOrderBySortOrder(parentId, openDataFilter);
        list = setInfo(list);
        // 将查询数据缓存到redis
        redisTemplate.opsForValue().set(key,new Gson().toJson(list));
        return ResponseData.success(list);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ApiOperation(value = "添加")
    public ResponseData add(@ModelAttribute Depart depart){

        boolean d = departService.saveOrUpdate(depart);
        // 如果不是添加的一级 判断设置上级为父节点标识
        if(!CommonConstant.PARENT_ID.equals(depart.getParentId())){
            Depart parent = departService.getOne(new QueryWrapper<Depart>().eq("parentId",depart.getParentId()));
            if(parent.getIsParent()==null||!parent.getIsParent()){
                parent.setIsParent(true);
                departService.updateById(parent);
            }
        }
        // 更新缓存
        Set<String> keys = redisTemplate.keys("Depart::*");
        redisTemplate.delete(keys);
        return ResponseData.error("添加成功");
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ApiOperation(value = "编辑组织机构")
    public ResponseData edit(@ModelAttribute Depart depart,
                               @RequestParam(required = false) String[] mainHeader,
                               @RequestParam(required = false) String[] viceHeader){

        boolean d = departService.updateById(depart);
        // 更新组织机构负责人 先删除原数据
        headerDepartService.deleteByDepartId(depart.getId());
        for(String id:mainHeader){
           HeaderDepart hd = new HeaderDepart();
            hd.setUserId(id);
            hd.setDepartId(depart.getId());
            hd.setType(CommonConstant.HEADER_TYPE_MAIN);
            headerDepartService.save(hd);
        }
        for(String id:viceHeader){
            HeaderDepart dh = new HeaderDepart();
            dh.setUserId(id);
            dh.setDepartId(depart.getId());
            dh.setType(CommonConstant.HEADER_TYPE_VICE);
            headerDepartService.save(dh);
        }
        // 手动删除所有部门缓存
        Set<String> keys = redisTemplate.keys("Depart:" + "*");
        redisTemplate.delete(keys);
        // 删除所有用户缓存
        Set<String> keysSysUser = redisTemplate.keys("SysUser:" + "*");
        redisTemplate.delete(keysSysUser);
        return ResponseData.error("编辑成功");
    }

    @RequestMapping(value = "/delByIds/{ids}", method = RequestMethod.DELETE)
    @ApiOperation(value = "批量通过id删除")
    public ResponseData delByIds(@PathVariable String[] ids){

        for(String id:ids){
            List<SysUser> list = userService.findByDepartmentId(id);
            if(list!=null&&list.size()>0){
                return ResponseData.error("删除失败，包含正被用户使用关联的部门");
            }
        }
        for(String id:ids){
            departService.removeById(id);
            // 删除关联数据权限
            roleDepartService.deleteByDepartId(id);
            // 删除关联部门负责人
            headerDepartService.deleteByDepartId(id);
            // 删除流程关联节点--工作流相关
//            actNodeService.deleteByRelateId(id);
        }
        // 手动删除所有部门缓存
        Set<String> keys = redisTemplate.keys("Depart:" + "*");
        redisTemplate.delete(keys);
        // 删除数据权限缓存
        Set<String> keysSysUserRoleData = redisTemplate.keys("SysUserRole::depIds:" + "*");
        redisTemplate.delete(keysSysUserRoleData);
        return ResponseData.error("批量通过id删除数据成功");
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    @ApiOperation(value = "部门名模糊搜索")
    public ResponseData searchByTitle(@RequestParam String title,
                                                  @ApiParam("是否开始数据权限过滤") @RequestParam(required = false, defaultValue = "true") Boolean openDataFilter){

        List<Depart> list = departService.findByDepartNameLikeOrderBySortOrder("%"+title+"%", openDataFilter);
        list = setInfo(list);
        return ResponseData.success(list);
    }

    public List<Depart> setInfo(List<Depart> list){

        // lambda表达式
        list.forEach(item -> {
            if(!CommonConstant.PARENT_ID.equals(item.getParentId())){
                Depart parent = departService.getById(item.getParentId());
                item.setParentDeaprtName(parent.getDepartname());
            }else{
                item.setParentDeaprtName("一级部门");
            }
            // 设置负责人
            item.setMainHeader(headerDepartService.findHeaderByDepartId(item.getId(), CommonConstant.HEADER_TYPE_MAIN));
            item.setViceHeader(headerDepartService.findHeaderByDepartId(item.getId(), CommonConstant.HEADER_TYPE_VICE));
        });
        return list;
    }
}