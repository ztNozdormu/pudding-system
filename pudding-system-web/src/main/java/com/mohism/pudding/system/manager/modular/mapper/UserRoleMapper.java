package com.mohism.pudding.system.manager.modular.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mohism.pudding.system.manager.entity.Role;
import com.mohism.pudding.system.manager.entity.SysUser;
import com.mohism.pudding.system.manager.entity.UserRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * <p>
 *  用户角色关联mapper 接口
 * </p>
 *
 * @author real earth
 * @since 2019-07-04
 */
public interface UserRoleMapper extends BaseMapper<UserRole> {

    /**
     * 通过角色id获取
     * @param roleId
     * @return
     */
    List<SysUser> findByRoleId(@Param("roleId") String roleId);

    /**
     * 通过用户id获取
     * @param userId
     * @return
     */
    List<Role> findByUserId(@Param("userId") String userId);

    /**
     * 通过用户id获取用户角色关联的部门数据
     * @param userId
     * @return
     */
    List<String> findDepIdsByUserId(@Param("userId") String userId);
}
