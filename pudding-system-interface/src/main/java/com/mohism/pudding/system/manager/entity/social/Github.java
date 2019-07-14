package com.mohism.pudding.system.manager.entity.social;

import com.baomidou.mybatisplus.annotation.TableName;
import com.mohism.pudding.kernel.base.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * <p>
 *  Github用户
 * </p>
 *
 * @author real earth
 * @since 2019-06-25
 */
@Data
@Entity
@Table(name = "user_github")
@TableName("user_github")
@ApiModel(value = "Github用户")
public class Github extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "github唯一id")
    private String openId;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "头像")
    private String avatar;

    @ApiModelProperty(value = "是否绑定账号 默认false")
    private Boolean isRelated = false;

    @ApiModelProperty(value = "绑定用户账号")
    private String relateUsername;
}