package com.mohism.pudding.system.manager.exception;

import lombok.Data;
import org.springframework.security.authentication.InternalAuthenticationServiceException;

/**
 * spring serurity 登录异常处理
 * @author real earth
 * @date   2019-06-30
 */
@Data
public class LoginFailLimitException extends InternalAuthenticationServiceException {

    private String msg;

    public LoginFailLimitException(String msg){
        super(msg);
        this.msg = msg;
    }

    public LoginFailLimitException(String msg, Throwable t) {
        super(msg, t);
        this.msg = msg;
    }
}
