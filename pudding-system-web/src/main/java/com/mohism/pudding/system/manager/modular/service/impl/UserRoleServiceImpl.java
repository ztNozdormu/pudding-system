package com.mohism.pudding.system.manager.modular.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mohism.pudding.kernel.model.auth.context.CommonConstant;
import com.mohism.pudding.system.manager.entity.Role;
import com.mohism.pudding.system.manager.entity.SysUser;
import com.mohism.pudding.system.manager.entity.UserRole;
import com.mohism.pudding.system.manager.modular.mapper.RoleMapper;
import com.mohism.pudding.system.manager.modular.mapper.SysUserMapper;
import com.mohism.pudding.system.manager.modular.mapper.UserRoleMapper;
import com.mohism.pudding.system.manager.modular.service.UserRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 用户角色 业务实现类
 * </p>
 *
 * @author real earth
 * @since 2019-06-30
 */
@Slf4j
@Service
@Transactional
public class UserRoleServiceImpl  extends ServiceImpl<UserRoleMapper,UserRole> implements UserRoleService {

    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private RoleMapper    roleMapper;


    @Override
    public List<UserRole> findByRoleId(String roleId) {

        return  this.list(new QueryWrapper<UserRole>().eq("roleId",roleId));
    }

    @Override
    public List<SysUser> findSysUserByRoleId(String roleId) {
//
//        List<UserRole>  userRoleList = this.list(new QueryWrapper<UserRole>().eq("roleId",roleId));
//        List<SysUser> list = new ArrayList<>();
//        for(UserRole ur : userRoleList){
//            SysUser u =  sysUserMapper.selectOne(new QueryWrapper<SysUser>().eq("userId",ur.getUserId()));
//            if(u!=null&&CommonConstant.USER_STATUS_NORMAL.equals(u.getStatus())){
//                list.add(u);
//            }
//        }
//        return list;
        return this.baseMapper.findByRoleId(roleId);
    }

    @Override
    public List<Role> findBySysUserId(String sysUserId){
//        List<UserRole>  userRoleList = this.list(new QueryWrapper<UserRole>().eq("userId",sysUserId));
//        List<Role> list = new ArrayList<>();
//        for(UserRole ur : userRoleList){
//            Role r = roleMapper.selectOne(new QueryWrapper<Role>().eq("roleId",ur.getRoleId()));
//            if(r!=null){
//                list.add(r);
//            }
//        }
//        return list;
       return this.baseMapper.findByUserId(sysUserId);
    }
    @Override
    public boolean deleteBySysUserId(String sysUseId) {
      return  this.remove(new QueryWrapper<UserRole>().eq("userId",sysUseId));
    }
    @Override
    public List<String> findDepIdsByUserId(String sysUseId){

        return this.baseMapper.findDepIdsByUserId(sysUseId);
    }

}
