package com.imantou.auth.config;

import com.imantou.auth.filter.CaptchaFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import javax.annotation.Resource;
import javax.servlet.DispatcherType;

/**
 * 验证码过滤器配置
 */
//@Configuration
public class CaptchaFilterConfig {

//    @Resource
    private CaptchaFilter captchaFilter;

    @Bean
    @Lazy
    public FilterRegistrationBean<CaptchaFilter> filterRegistration() {
        FilterRegistrationBean<CaptchaFilter> registration = new FilterRegistrationBean<>();
        // 添加过滤器
        registration.setFilter(captchaFilter);
        // 设置过滤路径
        registration.addUrlPatterns("/login/*");
        registration.setName("captchaFilter");
        // 设置优先级
        registration.setOrder(0);
        registration.setDispatcherTypes(DispatcherType.REQUEST);
        return registration;
    }

}