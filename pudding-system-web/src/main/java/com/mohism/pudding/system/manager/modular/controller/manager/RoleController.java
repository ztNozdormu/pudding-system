package com.mohism.pudding.system.manager.modular.controller.manager;

import com.mohism.pudding.kernel.model.reqres.response.ResponseData;
import com.mohism.pudding.system.manager.entity.Role;
import com.mohism.pudding.system.manager.entity.RoleDepart;
import com.mohism.pudding.system.manager.entity.RolePermission;
import com.mohism.pudding.system.manager.entity.UserRole;
import com.mohism.pudding.system.manager.modular.service.RoleDepartService;
import com.mohism.pudding.system.manager.modular.service.RolePermissionService;
import com.mohism.pudding.system.manager.modular.service.RoleService;
import com.mohism.pudding.system.manager.modular.service.UserRoleService;
import com.mohism.pudding.system.manager.vo.PageVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * <p>
 *  角色管理
 * </p>
 *
 * @author real earth
 * @since 2019-07-10
 */
@Slf4j
@RestController
@Api(description = "角色管理接口")
@RequestMapping("/pudding/role")
@Transactional
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private RolePermissionService rolePermissionService;

    @Autowired
    private RoleDepartService roleDepartService;

//    @Autowired
//    private ActNodeService actNodeService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @RequestMapping(value = "/getAllList", method = RequestMethod.GET)
    @ApiOperation(value = "获取全部角色")
    public ResponseData roleGetAll(){

        List<Role> list = roleService.list();
        return ResponseData.success(list);
    }

    @RequestMapping(value = "/getAllByPage", method = RequestMethod.GET)
    @ApiOperation(value = "分页获取角色")
    public ResponseData getRoleByPage(@ModelAttribute PageVo page){

//        Page<Role> list = roleService.findAll(PageUtil.initPage(page));
        List<Role> list = roleService.list();
        for(Role role : list){
            // 角色拥有权限
            List<RolePermission> permissions = rolePermissionService.findByRoleId(role.getId());
            role.setPermissions(permissions);
            // 角色拥有数据权限
            List<RoleDepart> departs = roleDepartService.findByRoleId(role.getId());
            role.setDepartments(departs);
        }
        return ResponseData.success(list);
    }

    @RequestMapping(value = "/setDefault", method = RequestMethod.POST)
    @ApiOperation(value = "设置或取消默认角色")
    public ResponseData setDefault(@RequestParam String id,
                                     @RequestParam Boolean isDefault){

        Role role = roleService.getById(id);
        if(role==null){
            return ResponseData.error("角色不存在");
        }
        role.setDefaultRole(isDefault);
        roleService.updateById(role);
        return ResponseData.success("设置成功");
    }

    @RequestMapping(value = "/editRolePerm", method = RequestMethod.POST)
    @ApiOperation(value = "编辑角色分配菜单权限")
    public ResponseData editRolePerm(@RequestParam String roleId,
                                       @RequestParam(required = false) String[] permIds){

        //删除其关联权限
        rolePermissionService.deleteByRoleId(roleId);
        //分配新权限
        for(String permId : permIds){
            RolePermission rolePermission = new RolePermission();
            rolePermission.setRoleId(roleId);
            rolePermission.setPermissionId(permId);
            rolePermissionService.save(rolePermission);
        }
        //手动批量删除缓存
        Set<String> keysUser = redisTemplate.keys("user:" + "*");
        redisTemplate.delete(keysUser);
        Set<String> keysUserRole = redisTemplate.keys("userRole:" + "*");
        redisTemplate.delete(keysUserRole);
        Set<String> keysUserPerm = redisTemplate.keys("userPermission:" + "*");
        redisTemplate.delete(keysUserPerm);
        Set<String> keysUserMenu = redisTemplate.keys("permission::userMenuList:*");
        redisTemplate.delete(keysUserMenu);
        return ResponseData.success(null);
    }

    @RequestMapping(value = "/editRoleDep", method = RequestMethod.POST)
    @ApiOperation(value = "编辑角色分配数据权限")
    public ResponseData editRoleDep(@RequestParam String roleId,
                                      @RequestParam Integer dataType,
                                      @RequestParam(required = false) String[] depIds){

        Role r = roleService.getById(roleId);
        r.setDataType(dataType);
        roleService.updateById(r);
        // 删除其关联数据权限
        roleDepartService.deleteByRoleId(roleId);
        // 分配新数据权限
        for(String depId : depIds){
            RoleDepart roleDepart = new RoleDepart();
            roleDepart.setRoleId(roleId);
            roleDepart.setDepartId(depId);
            roleDepartService.save(roleDepart);
        }
        // 手动删除相关缓存
        Set<String> keys = redisTemplate.keys("department:" + "*");
        redisTemplate.delete(keys);
        Set<String> keysUserRole = redisTemplate.keys("userRole:" + "*");
        redisTemplate.delete(keysUserRole);

        return ResponseData.success(null);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ApiOperation(value = "保存角色")
    public ResponseData save(@ModelAttribute Role role){

        boolean r = roleService.save(role);
        return ResponseData.success(role);
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ApiOperation(value = "更新数据")
    public ResponseData edit(@ModelAttribute Role role){

        boolean r = roleService.updateById(role);
        //手动批量删除缓存
        Set<String> keysUser = redisTemplate.keys("user:" + "*");
        redisTemplate.delete(keysUser);
        Set<String> keysUserRole = redisTemplate.keys("userRole:" + "*");
        redisTemplate.delete(keysUserRole);
        return ResponseData.success(role);
    }

    @RequestMapping(value = "/delAllByIds/{ids}", method = RequestMethod.DELETE)
    @ApiOperation(value = "批量通过ids删除")
    public ResponseData delByIds(@PathVariable String[] ids){

        for(String id:ids){
            List<UserRole> list = userRoleService.findByRoleId(id);
            if(list!=null&&list.size()>0){
                return ResponseData.error("删除失败，包含正被用户使用关联的角色");
            }
        }
        for(String id:ids){
            roleService.removeById(id);
            //删除关联菜单权限
            rolePermissionService.deleteByRoleId(id);
            //删除关联数据权限
            roleDepartService.deleteByRoleId(id);
            // 删除流程关联节点
//            actNodeService.deleteByRelateId(id);
        }
        return ResponseData.success("批量通过id删除数据成功");
    }

}
