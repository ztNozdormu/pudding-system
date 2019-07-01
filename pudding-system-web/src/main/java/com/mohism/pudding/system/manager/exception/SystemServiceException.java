package com.mohism.pudding.system.manager.exception;

import com.mohism.pudding.kernel.model.exception.AbstractBaseExceptionEnum;
import com.mohism.pudding.kernel.model.exception.ApiServiceException;

/**
 * 系统管理服务抛出的异常
 *
 * @author real earth
 * @date 2019-06-30-上午10:04
 */
public class SystemServiceException extends ApiServiceException {

    public SystemServiceException(AbstractBaseExceptionEnum exception) {
        super(exception);
    }

    @Override
    public String getExceptionClassName() {
        return SystemServiceException.class.getName();
    }

}
