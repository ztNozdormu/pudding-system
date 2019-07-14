package com.mohism.pudding.system.manager.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.mohism.pudding.kernel.base.entity.BaseEntity;
import com.mohism.pudding.kernel.model.auth.context.CommonConstant;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * <p>
 *  组织机构负责人
 * </p>
 *
 * @author real earth
 * @since 2019-06-25
 */
@Data
@Entity
@Table(name = "header_depart")
@TableName("header_depart")
@ApiModel(value = "组织机构负责人")
public class HeaderDepart extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "关联部门id")
    private String departId;

    @ApiModelProperty(value = "关联部门负责人")
    private String userId;

    @ApiModelProperty(value = "负责人类型 默认0主要 1副职")
    private Integer type = CommonConstant.HEADER_TYPE_MAIN;
}