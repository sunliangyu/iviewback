package com.qust.config;

import com.qust.intercerter.ResponseCode;
import com.qust.intercerter.TokenIntercepter;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
    //  registry.addInterceptor( new TokenIntercepter()).addPathPatterns("/**").excludePathPatterns("system/system/login","client/client/login","system/system/registry","client/client/registry");
      // registry.addInterceptor(new ResponseCode()).addPathPatterns("/**");
        super.addInterceptors(registry);
    }
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        super.addCorsMappings(registry);
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("*")
                .maxAge(3600)
                .allowCredentials(true);
    }

}