package com.sparta.taskflow.auth.jwt;

import com.sparta.taskflow.domain.user.type.RoleType;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtUtil {

    private final static long TOKEN_TIME = 10 * 60 * 1000L; // 토큰 유효시간: 10분
    private final static SignatureAlgorithm ALGORITHM = SignatureAlgorithm.HS256;

    @Value("${jwt.secret.key}")
    private String secretKey;
    private Key key;

    // 토큰 초기화
    @PostConstruct
    public void init() {
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(bytes);
    }

    public String createToken(Long userId, RoleType roleType) {
        Date date = new Date();

        return Jwts.builder().setSubject(userId.toString())
                   .setExpiration(new Date(date.getTime() + TOKEN_TIME))
                   .setIssuedAt(date)
                   .claim("role", roleType.getValue())
                   .signWith(key, ALGORITHM).compact();
    }

    public boolean validateToken(String token) {
        try {
            parseClaims(token);
            return true;
        } catch (ExpiredJwtException e) {       // 토큰이 만료된 경우
            log.error("만료된 토큰입니다.", e);
        } catch (SignatureException e) {        // 토큰 서명이 잘못된 경우
            log.error("서명이 유효하지 않은 토큰입니다.", e);
        } catch (MalformedJwtException | UnsupportedJwtException |
                 SecurityException e) {     // 잘못된 형식의 토큰이 전달된 경우
            log.error("잘못된 형식의 토큰입니다.", e);
        } catch (IllegalArgumentException e) {  // 토큰이 없는 경우
            log.error("잘못된 토큰입니다.", e);
        }

        return false;
    }

    public String getTokenFromRequest(HttpServletRequest request) {
        return request.getHeader("Authorization").substring(7);
    }

    public String extractUserIdFromToken(String token) {
        return parseClaims(token).getSubject();
    }

    public String extractUserRoleFromToken(String token) {
        return (String) parseClaims(token).get("role");
    }

    private Claims parseClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(key)           // 서명 검증을 위한 키
                   .build().parseClaimsJws(token)        // 유효성 검사 및 파싱
                   .getBody();
    }
}
