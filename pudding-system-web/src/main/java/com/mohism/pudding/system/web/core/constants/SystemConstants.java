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
package com.mohism.pudding.system.web.core.constants;

/**
 * 系统管理常量
 *
 * @author fengshuonan
 * @date 2018-08-26-下午4:00
 */
public interface SystemConstants {

    /**
     * 登录用户缓存的前缀
     */
    String LOGIN_USER_CACHE_PREFIX = "LOGIN_";

    /**
     * 登录超时时间（单位：秒）
     */
    Long DEFAULT_LOGIN_TIME_OUT_SECS = 3600L;
}
