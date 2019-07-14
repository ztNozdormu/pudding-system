package com.mohism.pudding.system.manager.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.mohism.pudding.kernel.base.entity.BaseEntity;
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
public class SysUser extends BaseEntity {

    private static final long serialVersionUID = 1L;
    /**
     * 用户名（登录账号）
     */
    @ApiModelProperty(value = "用户名")
    @Column(unique = true, nullable = false)
    private String userName;
    /**
     * 登录密码
     */
    @ApiModelProperty(value = "密码")
    private String password;
    /**
     * md5密码盐
     */
    private String salt;
    /**
     * 昵称
     */
    @ApiModelProperty(value = "昵称")
    private String nickName;
    /**
     * 真实姓名
     */
    @ApiModelProperty(value = "昵称")
    private String realName;
    /**
     * 手机号
     */
    @ApiModelProperty(value = "手机")
    private String mobile;
    /**
     * 邮箱地址
     */
    @ApiModelProperty(value = "邮件")
    private String email;
    /**
     * 生日
     */
    @ApiModelProperty(value = "生日")
    private Date birthday;
    /**
     *省市县地址（地区码）
     */
    @ApiModelProperty(value = "省市县地址")
    private String address;

    /**
     * 身份证号
     */
    @ApiModelProperty(value = "身份证号")
    private String citizenNo;
   /**
     * 街道详细地址
     */
    @ApiModelProperty(value = "街道地址")
    private String street;
    /**
     * 性别（M：男 F：女）
     */
    @ApiModelProperty(value = "性别")
    private String sex;
    /**
     * 密码强度
     */
    @ApiModelProperty(value = "密码强度")
    @Column(length = 2)
    private String passStrength;
    /**
     * 用户头像
     */
    @ApiModelProperty(value = "用户头像")
    @Column(length = 1000)
    private String avatar = CommonConstant.USER_DEFAULT_AVATAR;
    /**
     * 用户类型 0普通用户 1管理员
     */
    @ApiModelProperty(value = "用户类型 0普通用户 1管理员")
    private Integer type = CommonConstant.USER_TYPE_NORMAL;
    /**
     * 状态 默认0正常 -1拉黑
     */
    @ApiModelProperty(value = "状态 默认0正常 -1拉黑")
    private Integer status = CommonConstant.USER_STATUS_NORMAL;
    /**
     * 描述/详情/备注
     */
    @ApiModelProperty(value = "描述/详情/备注")
    private String description;
    /**
     * 所属一级组织机构Id
     */
    @ApiModelProperty(value = "所属部门id")
    private String departmentId;
    /**
     * 所属一级组织机构名称
     */
    @Transient
    @TableField(exist=false)
    @ApiModelProperty(value = "所属部门名称")
    private String departmentTitle;
    /**
     *用户拥有角色
     */
    @Transient
    @TableField(exist=false)
    @ApiModelProperty(value = "用户拥有角色")
    private List<Role> roles;
    /**
     * 用户拥有的权限
     */
    @Transient
    @TableField(exist=false)
    @ApiModelProperty(value = "用户拥有的权限")
    private List<Permission> permissions;
    /**
     * 导入数据时使用的默认角色
     */
    @Transient
    @TableField(exist=false)
    @ApiModelProperty(value = "导入数据时使用")
    private Integer defaultRole;
}
