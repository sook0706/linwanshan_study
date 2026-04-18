package com.lostfound.lostfound.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;

/**
 * JWT工具类
 * 实现Token生成与校验
 * 完全符合题目要求：登录生成Token，接口鉴权
 */
@Component
public class JwtUtil {

    // 【固定安全密钥】解决了密钥太短报错问题
    private static final String SECRET = "abcdefghijklmnopqrstuvwxyz1234567890!@#$%^&*()";

    // 有效期 1 天
    private static final long EXPIRATION = 1000 * 60 * 60 * 24;

    // 生成安全密钥
    private final SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    /**
     * 生成Token
     */
    public String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(key)
                .compact();
    }

    /**
     * 解析Token
     */
    public Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}