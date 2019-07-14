package com.mohism.pudding.system.manager.modular.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mohism.pudding.system.manager.entity.Permission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  菜单/权限 mapper接口
 * </p>
 *
 * @author real earth
 * @since 2019-07-07
 */
public interface PermissionMapper extends BaseMapper<Permission> {

    /**
     * 通过用户id获取
     * @param userId
     * @return
     */
    List<Permission> findBySysUserId(@Param("userId") String userId);
}
