package com.wugengkj.springboot.config;

import com.wugengkj.springboot.interceptor.ApiAccessValidInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * WebMvc配置
 *
 * @author leaf
 * <p>date: 2018-05-07 19:55</p>
 * <p>version: 1.0</p>
 */
@Configuration
public class DevWebMvcConfig extends WebMvcConfigurationSupport {

    @Autowired
    private ApiAccessValidInterceptor apiAccessValidInterceptor;
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(apiAccessValidInterceptor).addPathPatterns("/**").
                excludePathPatterns("/swagger-ui.html", "/webjars/**", "/druid/**", "/error/**", "/v2/api-docs", "/swagger-resources/**");
    }
}
