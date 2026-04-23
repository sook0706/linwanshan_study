package com.lostfound.lostfound.interceptor;

import com.lostfound.lostfound.common.R;
import com.lostfound.lostfound.util.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * 登录拦截器
 * 作用：校验管理员接口的Token
 */
public class LoginInterceptor implements HandlerInterceptor {

    // JWT工具类
    private final JwtUtil jwtUtil;

    public LoginInterceptor(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    // 接口访问前校验Token
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 获取请求头的Token
        String token = request.getHeader("Authorization");

        // 无Token 或 格式错误
        if (token == null || !token.startsWith("Bearer ")) {
            return err(response);
        }

        try {
            // 截取Token
            String realToken = token.substring(7);
            Claims claims = jwtUtil.extractClaims(realToken);

            // 把用户信息存入request
            request.setAttribute("userId", claims.get("userId"));
            request.setAttribute("role", claims.get("role"));

            return true;
        } catch (Exception e) {
            // Token无效
            return err(response);
        }
    }

    // 返回未登录提示
    private boolean err(HttpServletResponse response) throws Exception {
        response.setContentType("application/json;charset=utf-8");
        PrintWriter out = response.getWriter();
        out.write(new ObjectMapper().writeValueAsString(R.error("请先登录")));
        out.flush();
        out.close();
        return false;
    }
}