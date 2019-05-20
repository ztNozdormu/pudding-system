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
package com.mohism.pudding.system.web.modular.provider;

import com.mohism.pudding.kernel.model.api.ResourceService;
import com.mohism.pudding.kernel.model.resource.ResourceDefinition;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Set;

/**
 * 资源服务提供者
 *
 * @author fengshuonan
 * @date 2018-08-06-上午9:05
 */
@RestController
public class ResourceServiceProvider implements ResourceService {

    @Override
    public void reportResources(@RequestParam("appCode") String appCode,
                                @RequestBody Map<String, Map<String, ResourceDefinition>> resourceDefinitions) {

    }

    @Override
    public Set<String> getUserResourceUrls(@RequestParam("accountId") String accountId) {
        return null;
    }

    @Override
    public ResourceDefinition getResourceByUrl(@RequestParam("url") String url) {
        return null;
    }
}
