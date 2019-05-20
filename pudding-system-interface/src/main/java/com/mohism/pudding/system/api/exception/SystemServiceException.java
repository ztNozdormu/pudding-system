/**
 * Copyright 2018-2020 stylefeng & fengshuonan (sn93@qq.com)
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mohism.pudding.system.api.exception;


import com.mohism.pudding.kernel.model.exception.AbstractBaseExceptionEnum;
import com.mohism.pudding.kernel.model.exception.ApiServiceException;

/**
 * 系统管理服务抛出的异常
 *
 * @author fengshuonan
 * @date 2018-08-08-上午10:04
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
