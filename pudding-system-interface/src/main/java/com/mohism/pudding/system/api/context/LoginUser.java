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
package com.mohism.pudding.system.api.context;


import com.mohism.pudding.kernel.model.auth.AbstractLoginUser;
import lombok.Data;

import java.util.Set;


/**
 * 当前用户的登录信息
 *
 * @author fengshuonan
 * @Date 2018/8/22 下午6:19
 */
@SuppressWarnings("ALL")
@Data
public class LoginUser implements AbstractLoginUser {

    /**
     * 账号id
     */
    private Long accountId;

    /**
     * 应用id
     */
    private Long appId;

    /**
     * 角色id集合
     */
    private Set<Long> roleIds;

    /**
     * 角色编码集合
     */
    private Set<Long> roleCodes;

    /**
     * 可用资源集合
     */
    private Set<Long> resourceUrls;

    @Override
    public Long getUserUniqueId() {
        return accountId;
    }

    @Override
    public Long getAppId() {
        return appId;
    }

    @Override
    public Set<Long> getRoleIds() {
        return roleIds;
    }

    @Override
    public Set<Long> getRoleCodes() {
        return roleCodes;
    }

    @Override
    public Set<Long> getResourceUrls() {
        return resourceUrls;
    }
}
