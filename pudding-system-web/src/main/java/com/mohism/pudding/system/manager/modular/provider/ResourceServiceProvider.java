package com.mohism.pudding.system.manager.modular.provider;

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
 * @author real earth
 * @date 2019-06-30-上午9:05
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
