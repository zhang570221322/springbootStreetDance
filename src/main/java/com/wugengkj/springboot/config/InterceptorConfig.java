package com.wugengkj.springboot.config;

import com.wugengkj.springboot.interceptor.ApiAccessValidInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author leaf
 * <p>date: 2018-05-10 13:45</p>
 * <p>version: 1.0</p>
 */
@Configuration
@Profile("prod")
public class InterceptorConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private ApiAccessValidInterceptor apiAccessValidInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(apiAccessValidInterceptor).addPathPatterns("/**").
                excludePathPatterns("/swagger-ui.html", "/webjars/**", "/druid/**", "/error/**", "/v2/api-docs", "/swagger-resources/**");
    }
}
