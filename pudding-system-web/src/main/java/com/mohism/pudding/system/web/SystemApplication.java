package com.mohism.pudding.system.web;

import com.mohism.pudding.kernel.logger.util.LogUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 系统管理
 *
 * @author stylefeng
 * @Date 2018/1/22 21:27
 */
@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
@MapperScan("com.mohism.pudding.**.modular.mapper")
public class SystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(SystemApplication.class, args);
        LogUtil.info("system启动成功！");
    }
}
