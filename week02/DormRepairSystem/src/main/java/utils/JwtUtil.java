package com.dorm.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;

@Component
public class JwtUtil {
    //密钥（自定义）
    private static final SecretKey SECRET_KEY = Keys.hmacShaKeyFor("dormRepairSystem1234567890dormRepairSystem".getBytes());
    // Token过期时间：2小时
    private static final long EXPIRE_TIME = 2 * 60 * 60 * 1000;

    //生成Token：传入用户信息（如id、username、role）
    public String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE_TIME))
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
                .compact();
    }

    //解析Token，获取用户信息
    public Claims parseToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token.replace("Bearer ", "")) // 去掉前端可能加的Bearer前缀
                .getBody();
    }

    //校验Token是否过期
    public boolean isTokenExpired(Claims claims) {
        return claims.getExpiration().before(new Date());
    }
}