package com.lostfound.lostfound.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

@Component
public class JwtUtil {

    private static final String KEY = "abcdefghijklmnopqrstuvwxyz1234567890ABCDEF";
    private static final long EXPIRATION = 86400000L;

    public String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(Keys.hmacShaKeyFor(KEY.getBytes()))
                .compact();
    }

    public Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(KEY.getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // 从token获取userId
    public Long getUserIdFromToken(String token) {
        return extractClaims(token).get("userId", Long.class);
    }
    public Integer getRoleFromToken(String token) {
        return extractClaims(token).get("role", Integer.class);
    }
}