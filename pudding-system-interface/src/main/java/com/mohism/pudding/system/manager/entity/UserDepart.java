package com.mohism.pudding.system.manager.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.mohism.pudding.kernel.base.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**、
 * 用户组织机构关联表
 * @author real earth
 * @date   2019-06-30
 */
@Data
@Entity
@Table(name = "user_depart")
@TableName("user_depart")
@ApiModel(value = "用户角色")
public class UserDepart extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户唯一id")
    private String userId;

    @ApiModelProperty(value = "组织机构唯一id")
    private String departId;

    @Transient
    @TableField(exist=false)
    @ApiModelProperty(value = "组织机构名称")
    private String departName;
}
