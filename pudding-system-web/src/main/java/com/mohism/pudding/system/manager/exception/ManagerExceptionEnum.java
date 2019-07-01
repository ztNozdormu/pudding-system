package com.mohism.pudding.system.manager.exception;


import com.mohism.pudding.kernel.model.exception.AbstractBaseExceptionEnum;

/**
 * 系统基础管理异常枚举
 *
 * @author real earth
 * @data 2019/06/26 0:07
 */
public enum ManagerExceptionEnum implements AbstractBaseExceptionEnum {

    REPEAT_DICT_TYPE(2110, "该编码字典已经存在！"),
    NOT_EXISTED(2111, "字典不存在！"),
    PARENT_NOT_EXISTED(2112, "父级字典不存在！"),
    WRONG_STATUS(2113, "状态错误！"),
    LOGIN_COUNT_OUT(21114,"登录错误超次数"),
    NO_SEND_EMAIL(2115,"您还未配置发送邮箱"),
    NO_QN_OSS(2115,"您还未配置七牛云");


    ManagerExceptionEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    private int code;

    private String message;

    @Override
    public Integer getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
