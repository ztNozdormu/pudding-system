package com.mohism.pudding.system.manager.core.constants;

import com.mohism.pudding.kernel.model.exception.AbstractBaseExceptionEnum;

/**
 * 鉴权相关的错误异常枚举
 *
 * @author real earth
 * @date 2019-06-30-下午3:19
 */
public enum AuthExceptionEnum implements AbstractBaseExceptionEnum {

    USER_NOT_FOUND(3110, "用户不存在！"),

    INVALID_PWD(3111, "密码错误！");

    private int code;
    private String message;

    AuthExceptionEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public Integer getCode() {
        return this.code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
