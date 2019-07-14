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
 *  微博用户
 * </p>
 *
 * @author real earth
 * @since 2019-06-25
 */
@Data
@Entity
@Table(name = "user_weibo")
@TableName("user_weibo")
@ApiModel(value = "微博用户")
public class Weibo extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "微博唯一id")
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