package com.mohism.pudding.system.manager.config.security;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * 忽略不做拦截的请求路径集合
 * @author real earth
 * @date   2019-06-30
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "ignored")
public class IgnoredUrlsProperties {

    private List<String> urls = new ArrayList<>();
}
