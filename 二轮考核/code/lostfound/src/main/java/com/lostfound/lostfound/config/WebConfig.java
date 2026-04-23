package com.lostfound.lostfound.config;

import com.lostfound.lostfound.interceptor.LoginInterceptor;
import com.lostfound.lostfound.util.JwtUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web配置类
 * 作用：注册登录拦截器，配置哪些接口需要校验Token
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    // 注入JWT工具类，用于生成和解析Token
    private final JwtUtil jwtUtil;

    /**
     * 构造方法注入JwtUtil
     */
    public WebConfig(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    /**
     * 添加拦截器配置
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor(jwtUtil))
                // 拦截所有 /admin/ 开头的管理员接口
                .addPathPatterns("/admin/**")
                // 排除登录、注册接口，这两个不需要Token就能访问
                .excludePathPatterns("/user/login", "/user/register");
    }
}