package com.mohism.pudding.system.manager.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.mohism.pudding.kernel.base.entity.BaseEntity;
import com.mohism.pudding.kernel.model.auth.context.CommonConstant;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;

/**
 * 系统角色表
 * @author real earth
 * @date   2019-06-30
 */
@Data
@Entity
@Table(name = "sys_role")
@TableName("sys_role")
@ApiModel(value = "角色")
public class Role extends BaseEntity {

    private static final long serialVersionUID = 1L;
    /**
     * 角色名称
     */
    @ApiModelProperty(value = "角色名 以ROLE_开头")
    private String roleName;
    /**
     * 角色名称编码
     */
    @ApiModelProperty(value = "角色名称编码")
    private String roleCode;
    /**
     * 默认角色
     */
    @ApiModelProperty(value = "是否为注册默认角色")
    private Boolean defaultRole;
    /**
     * 数据权限类型 0全部默认 1自定义
     */
    @ApiModelProperty(value = "数据权限类型 0全部默认 1自定义")
    private Integer dataType = CommonConstant.DATA_TYPE_ALL;
    /**
     * 默认角色
     */
    @ApiModelProperty(value = "备注说明")
    private String description;

    @Transient
    @TableField(exist=false)
    @ApiModelProperty(value = "拥有权限")
    private List<RolePermission> permissions;

    @Transient
    @TableField(exist=false)
    @ApiModelProperty(value = "拥有数据权限")
    private List<RoleDepart> departments;
}
