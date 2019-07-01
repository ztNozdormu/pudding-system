package com.mohism.pudding.system.manager.config;

import com.mohism.pudding.system.manager.core.db.DepartInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 系统基础管理的自动配置
 *
 *
 * @author real earth
 * @date 2019-06-30-下午5:12
 */
@Configuration
public class ManagerAutoConfiguration {

    /**
     * 数据库初始化
     */
//    @Bean
//    public DictInitializer dictInitializer() {
//        return new DictInitializer();
//    }

    @Bean
    public DepartInitializer dictTypeInitializer() {
        return new DepartInitializer();
    }

//    /**
//     * 控制器
//     */
//    @Bean
//    public DictController dictController() {
//        return new DictController();
//    }
//
//    @Bean
//    public DictTypeController dictTypeController() {
//        return new DictTypeController();
//    }
//
//    /**
//     * 服务层
//     */
//    @Bean
//    public DictService dictService() {
//        return new DictService();
//    }
//
//    @Bean
//    public DictTypeService dictTypeService() {
//        return new DictTypeService();
//    }
//
//    /**
//     * 服务提供者
//     */
//    @Bean
//    public DictServiceProvider dictServiceProvider() {
//        return new DictServiceProvider();
//    }
//
//    @Bean
//    public DictTypeServiceProvider dictTypeServiceProvider() {
//        return new DictTypeServiceProvider();
//    }
}
