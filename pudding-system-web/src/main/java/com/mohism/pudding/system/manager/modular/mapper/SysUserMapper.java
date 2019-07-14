package com.mohism.pudding.system.manager.modular.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mohism.pudding.base.dict.api.model.DictInfo;
import com.mohism.pudding.system.manager.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author real earth
 * @since 2019-06-30
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {

    /**
     * 获取用户列表
     *
     * @author real earth
     * @Date 2019/07/04 晚上22:39
     */
    List<SysUser> findUserByCondition(Page page, SysUser sysUser);
}
