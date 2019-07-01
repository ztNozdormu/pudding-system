package com.mohism.pudding.system.manager.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.persistence.Entity;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

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
@TableName("sys_depart")
@ApiModel(value = "系统组织机构")
public class Depart implements Serializable {

    private static final long serialVersionUID = 1L;

        /**
         * 组织机构名称
         */
        @TableField("departname")
        private String departname;

        /**
         * 父组织ID
         */
        @TableField("parent_id")
        private String parentId;

        /**
         * 是否是父级别组织
         */
        @TableField("is_parent")
        private Integer isParent;

        /**
         * 组织机构编码
         */
        @TableField("code")
        private String code;

        /**
         * 组织机构类型
         */
        @TableField("type")
        private Integer type;

        /**
         * 组织机构状态
         */
        @TableField("status")
        private Integer status;

        /**
         * 组织机构联系电话
         */
        @TableField("mobile")
        private String mobile;

        /**
         * 组织机构传真地址
         */
        @TableField("fax")
        private String fax;

        /**
         * 组织机构英文名称
         */
        @TableField("departname_en")
        private String departnameEn;

        /**
         * 组织机构英文简写
         */
        @TableField("departname_abbr")
        private String departnameAbbr;

        /**
         * 组织机构地址["510000","510100","510114"]
         */
        @TableField("address")
        private String address;

        /**
         * 详细街道地址
         */
        @TableField("street")
        private String street;

        /**
         * 组织机构描述
         */
        @TableField("description")
        private String description;

        /**
         * 排序序号
         */
        @TableField("sort_order")
        private BigDecimal sortOrder;

        /**
         * 删除标记
         */
        @TableField("del_flag")
        private Integer delFlag;

        /**
         * 创建人
         */
        @TableField("create_by")
        private String createBy;

        /**
         * 修改人
         */
        @TableField("update_by")
        private String updateBy;

        /**
         * 创建时间
         */
        @TableField("create_time")
        private LocalDateTime createTime;

        /**
         * 修改时间
         */
        @TableField("update_time")
        private LocalDateTime updateTime;

}
