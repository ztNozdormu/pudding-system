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
package com.mohism.pudding.system.api.exception.enums;


import com.mohism.pudding.kernel.model.exception.AbstractBaseExceptionEnum;

/**
 * 鉴权相关的错误异常
 *
 * @author fengshuonan
 * @date 2018-08-26-下午3:19
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
