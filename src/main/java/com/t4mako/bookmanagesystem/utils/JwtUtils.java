package com.t4mako.bookmanagesystem.utils;

/**
 * @author T4mako
 * @date 2023/6/6 10:30
 */
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JwtUtils {
    // 秘钥
    private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    // 过期时间：24小时
    private static final long EXPIRATION_TIME = 24 * 60 * 60 * 1000;

    /**
     * 生成 JWT（JSON Web Token）
     */
    public static String generateToken(String username, String password) {
        // 获取当前时间
        Date now = new Date();

        // 设置过期时间
        Date expiration = new Date(now.getTime() + EXPIRATION_TIME);

        // 创建 JWT 构建器
        JwtBuilder builder = Jwts.builder()
                .setSubject(username)
                .claim("password", password)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(SECRET_KEY);

        // 生成 JWT
        return builder.compact();
    }

    /**
     * 解析 JWT，获取包含用户名和密码的声明
     */
    public static Claims parseToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * 正则表达式Token取值
     */
    public static String extractValue(String input, String key) {
        String regex = key + "=([^,}]+)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }
}

