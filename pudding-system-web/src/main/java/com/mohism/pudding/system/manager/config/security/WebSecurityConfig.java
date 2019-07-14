package com.mohism.pudding.system.manager.config.security;


import com.mohism.pudding.system.manager.config.security.jwt.AuthenticationFailHandler;
import com.mohism.pudding.system.manager.config.security.jwt.AuthenticationSuccessHandler;
import com.mohism.pudding.system.manager.config.security.jwt.JWTAuthenticationFilter;
import com.mohism.pudding.system.manager.config.security.jwt.RestAccessDeniedHandler;
import com.mohism.pudding.system.manager.config.security.permission.MyFilterSecurityInterceptor;
import com.mohism.pudding.system.manager.modular.service.SecurityService;
import com.mohism.pudding.system.manager.modular.service.impl.UserDetailsServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

/**
 * Security 核心配置类
 * 开启注解控制权限至Controller
 * @author real earth
 * @datre  2019-06-30
 */
@Slf4j
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${pudding.token.redis}")
    private Boolean tokenRedis;

    @Value("${pudding.tokenExpireTime}")
    private Integer tokenExpireTime;

    @Value("${pudding.token.storePerms}")
    private Boolean storePerms;

    @Autowired
    private IgnoredUrlsProperties ignoredUrlsProperties;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private AuthenticationSuccessHandler successHandler;

    @Autowired
    private AuthenticationFailHandler failHandler;

    @Autowired
    private RestAccessDeniedHandler accessDeniedHandler;

    @Autowired
    private MyFilterSecurityInterceptor myFilterSecurityInterceptor;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private SecurityService securityService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry = http
                .authorizeRequests();

        //除配置文件忽略路径其它所有请求都需经过认证和授权
        for(String url:ignoredUrlsProperties.getUrls()){
            registry.antMatchers(url).permitAll();
        }

        registry.and()
                //表单登录方式
                .formLogin()
                .loginPage("/pudding/common/needLogin")
                //登录请求url
                .loginProcessingUrl("/pudding/login")
                .permitAll()
                //成功处理类
                .successHandler(successHandler)
                //失败
                .failureHandler(failHandler)
                .and()
                //允许网页iframe
                .headers().frameOptions().disable()
                .and()
                .logout()
                .permitAll()
                .and()
                .authorizeRequests()
                //任何请求
                .anyRequest()
                //需要身份认证
                .authenticated()
                .and()
                //关闭跨站请求防护
                .csrf().disable()
                //前后端分离采用JWT 不需要session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                //自定义权限拒绝处理类
                .exceptionHandling().accessDeniedHandler(accessDeniedHandler)
                .and()
                //添加自定义权限过滤器
                .addFilterBefore(myFilterSecurityInterceptor, FilterSecurityInterceptor.class)
                //添加JWT认证过滤器
                .addFilter(new JWTAuthenticationFilter(authenticationManager(), tokenRedis, tokenExpireTime, storePerms,
                        redisTemplate, securityService));
    }
}
