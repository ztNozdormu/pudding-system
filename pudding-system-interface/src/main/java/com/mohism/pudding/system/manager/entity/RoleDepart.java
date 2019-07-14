package com.mohism.pudding.system.manager.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.mohism.pudding.kernel.base.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 角色部门关联表
 * @author real earth
 * @date   2019-06-30
 */
@Data
@Entity
@Table(name = "role_depart")
@TableName("role_depart")
@ApiModel(value = "角色部门")
public class RoleDepart extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "角色id")
    private String roleId;

    @ApiModelProperty(value = "部门id")
    private String departId;
}