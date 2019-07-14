package com.mohism.pudding.system.manager.modular.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mohism.pudding.core.page.PageFactory;
import com.mohism.pudding.system.manager.entity.Depart;
import com.mohism.pudding.system.manager.entity.Permission;
import com.mohism.pudding.system.manager.entity.Role;
import com.mohism.pudding.system.manager.entity.SysUser;
import com.mohism.pudding.system.manager.modular.mapper.DepartMapper;
import com.mohism.pudding.system.manager.modular.mapper.PermissionMapper;
import com.mohism.pudding.system.manager.modular.mapper.SysUserMapper;
import com.mohism.pudding.system.manager.modular.mapper.UserRoleMapper;
import com.mohism.pudding.system.manager.modular.service.SecurityService;
import com.mohism.pudding.system.manager.modular.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 用户 业务实现类
 * </p>
 *
 * @author real earth
 * @since 2019-06-30
 */
@Slf4j
@Service
@Transactional
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {


    @Autowired
    private UserRoleMapper   userRoleMapper;

    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private DepartMapper departMapper;

    @Autowired
    private SecurityService securityService;


    @Override
    public SysUser findBySysUsername(String userName) {

        List<SysUser> list = this.list(new QueryWrapper<SysUser>().eq("user_name", userName));
        if(list!=null&&list.size()>0){
            SysUser SysUser = list.get(0);
            // 关联部门--更改为递归级联查询
            if(StrUtil.isNotBlank(SysUser.getDepartmentId())){
                Depart depart = departMapper.selectById(SysUser.getDepartmentId());
                SysUser.setDepartmentTitle(depart.getDepartname());
            }
            // 关联角色
            List<Role> roleList = userRoleMapper.findByUserId(SysUser.getId());
            SysUser.setRoles(roleList);
            // 关联权限菜单
            List<Permission> permissionList = permissionMapper.findBySysUserId(SysUser.getId());
            SysUser.setPermissions(permissionList);
            return SysUser;
        }
        return null;
    }

    @Override
    public SysUser findByMobile(String mobile) {

        List<SysUser> list = this.list(new QueryWrapper<SysUser>().eq("mobile", mobile));
        if(list!=null&&list.size()>0) {
            SysUser SysUser = list.get(0);
            return SysUser;
        }
        return null;
    }

    @Override
    public SysUser findByEmail(String email) {

        List<SysUser> list = this.list(new QueryWrapper<SysUser>().eq("email", email));
        if(list!=null&&list.size()>0) {
            SysUser SysUser = list.get(0);
            return SysUser;
        }
        return null;
    }

    @Override
    public List<SysUser> findUserByCondition(Page page, SysUser sysUser){
        if (page == null) {
            page = PageFactory.defaultPage();
        }

        if (sysUser == null) {
            sysUser = new SysUser();
        }

        // 后面补充 查看当前用户拥有的数据权限以及时间范围条件
 //     List<String> depIds = securityUtil.getDeparmentIds();
//                if(depIds!=null&&depIds.size()>0){
//                    list.add(departmentIdField.in(depIds));
//                }
        return this.baseMapper.findUserByCondition(page, sysUser);
    }
    @Override
    public List<SysUser> findByDepartmentId(String departmentId) {

        return this.list(new QueryWrapper<SysUser>().eq("departmentId", departmentId));
    }

    @Override
    public List<SysUser> findBySysUsernameLikeAndStatus(String userName, Integer status) {
        return this.list(new QueryWrapper<SysUser>().eq("userName", userName).eq("status",status));
    }
}
