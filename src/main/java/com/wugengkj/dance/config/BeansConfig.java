package com.wugengkj.dance.config;

import com.wugengkj.dance.interceptor.ApiAccessValidInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

/**
 * @author leaf
 * <p>date: 2018-02-09 0:30</p>
 * <p>version: 1.0</p>
 */
@Configuration
public class BeansConfig {

    @Profile("prod")
    @Bean
    public ApiAccessValidInterceptor apiAccessValidInterceptor() {
        return new ApiAccessValidInterceptor();
    }

    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() {
        return new MethodValidationPostProcessor();
    }
}
