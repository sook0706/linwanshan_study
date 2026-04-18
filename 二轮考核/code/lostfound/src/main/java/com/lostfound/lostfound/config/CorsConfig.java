package com.lostfound.lostfound.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 跨域配置
 * 解决前端访问后端接口的跨域问题
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 对所有接口生效
        registry.addMapping("/**")
                // 允许所有域名访问
                .allowedOriginPatterns("*")
                // 允许所有请求方式
                .allowedMethods("*")
                // 允许携带cookie
                .allowCredentials(true)
                // 最大有效时间
                .maxAge(3600);
    }
}
