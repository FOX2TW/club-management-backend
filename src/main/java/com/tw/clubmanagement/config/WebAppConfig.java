package com.tw.clubmanagement.config;

import com.tw.clubmanagement.annotation.AccessPermissionInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebAppConfig extends WebMvcConfigurerAdapter {
    private final AccessPermissionInterceptor accessPermissionInterceptor;

    @Autowired
    public WebAppConfig(AccessPermissionInterceptor accessPermissionInterceptor) {
        this.accessPermissionInterceptor = accessPermissionInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(accessPermissionInterceptor).addPathPatterns("/**");
    }
}
