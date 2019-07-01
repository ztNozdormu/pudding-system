package com.mohism.pudding.system.manager.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.mohism.pudding.kernel.model.auth.context.CommonConstant;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author real earth
 * @since 2019-06-30
 */
@Data
@Entity
@Table(name = "sys_user")
@TableName("sys_user")
@ApiModel(value = "角色权限")
public class SysUser implements Serializable {

    private static final long serialVersionUID = 1L;

//    /**
//     * 主键id
//     */
//    @TableId("USER_ID")
//    private Long userId;
//    /**
//     * 头像
//     */
//    @TableField("AVATAR")
//    private String avatar;
//    /**
//     * 账号
//     */
//    @TableField("ACCOUNT")
//    private String account;
//    /**
//     * 密码
//     */
//    @TableField("PASSWORD")
//    private String password;
//    /**
//     * md5密码盐
//     */
//    @TableField("SALT")
//    private String salt;
//    /**
//     * 名字
//     */
//    @TableField("NAME")
//    private String name;
//    /**
//     * 生日
//     */
//    @TableField("BIRTHDAY")
//    private Date birthday;
//    /**
//     * 性别（M：男 F：女）
//     */
//    @TableField("SEX")
//    private String sex;
//    /**
//     * 电子邮件
//     */
//    @TableField("EMAIL")
//    private String email;
//    /**
//     * 电话
//     */
//    @TableField("PHONE")
//    private String phone;
//    /**
//     * 状态(1：启用  2：冻结  3：删除）
//     */
//    @TableField("STATUS")
//    private Integer status;
//    /**
//     * 创建时间
//     */
//    @TableField(value = "CREATE_TIME", fill = FieldFill.INSERT)
//    private Date createTime;
//    /**
//     * 更新时间
//     */
//    @TableField(value = "UPDATE_TIME", fill = FieldFill.UPDATE)
//    private Date updateTime;


    @ApiModelProperty(value = "用户名")
    @Column(unique = true, nullable = false)
    private String username;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "昵称")
    private String nickName;

    @ApiModelProperty(value = "手机")
    private String mobile;

    @ApiModelProperty(value = "邮件")
    private String email;

    @ApiModelProperty(value = "省市县地址")
    private String address;

    @ApiModelProperty(value = "街道地址")
    private String street;

    @ApiModelProperty(value = "性别")
    private String sex;

    @ApiModelProperty(value = "密码强度")
    @Column(length = 2)
    private String passStrength;

    @ApiModelProperty(value = "用户头像")
    @Column(length = 1000)
    private String avatar = CommonConstant.USER_DEFAULT_AVATAR;

    @ApiModelProperty(value = "用户类型 0普通用户 1管理员")
    private Integer type = CommonConstant.USER_TYPE_NORMAL;

    @ApiModelProperty(value = "状态 默认0正常 -1拉黑")
    private Integer status = CommonConstant.USER_STATUS_NORMAL;

    @ApiModelProperty(value = "描述/详情/备注")
    private String description;

    @ApiModelProperty(value = "所属部门id")
    private String departmentId;

    @Transient
    @TableField(exist=false)
    @ApiModelProperty(value = "所属部门名称")
    private String departmentTitle;

    @Transient
    @TableField(exist=false)
    @ApiModelProperty(value = "用户拥有角色")
    private List<Role> roles;

    @Transient
    @TableField(exist=false)
    @ApiModelProperty(value = "用户拥有的权限")
    private List<Permission> permissions;

    @Transient
    @TableField(exist=false)
    @ApiModelProperty(value = "导入数据时使用")
    private Integer defaultRole;
}
