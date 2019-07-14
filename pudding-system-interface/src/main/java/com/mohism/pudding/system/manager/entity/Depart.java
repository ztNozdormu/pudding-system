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
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 *  组织机构表
 * </p>
 *
 * @author real earth
 * @since 2019-06-25
 */
@Data
@Entity
@Table(name ="sys_depart")
@TableName("sys_depart")
@ApiModel(value = "系统组织机构")
public class Depart extends BaseEntity {
    private static final long serialVersionUID = 1L;

        /**
         * 组织机构名称
         */
        private String departname;

        /**
         * 父组织ID
         */
        private String parentId;

        /**
         * 是否是父级别组织
         */
        private Boolean isParent;

        /**
         * 组织机构编码
         */
        private String code;

        /**
         * 组织机构类型
         */
        private Integer type;

        /**
         * 组织机构状态
         */
        private Integer status;

        /**
         * 组织机构联系电话
         */
        private String mobile;

        /**
         * 组织机构传真地址
         */
        private String fax;

        /**
         * 组织机构英文名称
         */
        private String departnameEn;

        /**
         * 组织机构英文简写
         */
        private String departnameAbbr;

        /**
         * 组织机构地址["510000","510100","510114"]
         */
        private String address;

        /**
         * 详细街道地址
         */
        private String street;

        /**
         * 组织机构描述
         */
        private String description;

        /**
         * 排序序号
         */
        private BigDecimal sortOrder;
        /**
         *父节点机构名称
         */
        @Transient
        @TableField(exist=false)
        @ApiModelProperty(value = "父节点机构名称")
        private String parentDeaprtName;

        @Transient
        @TableField(exist=false)
        @ApiModelProperty(value = "主负责人")
        private List<String> mainHeader;

        @Transient
        @TableField(exist=false)
        @ApiModelProperty(value = "副负责人")
        private List<String> viceHeader;
}
