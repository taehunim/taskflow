package com.sparta.taskflow.auth.jwt;

import com.sparta.taskflow.global.exception.JwtAuthenticationException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * 토큰이 존재하는 경우에 userId를 Spring Security User 타입에 담아 SecurityContext에 넣어준다.
 * 토큰이 존재하지 않거나 유효하지 않은 토큰인 경우 오류 메시지를 request attribute에 담아
 * JwtAuthenticationEntryPoint에서 사용할 수 있도록 한다.
 */
@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {

        /*
         토큰이 없거나 잘못된 토큰일 때 다음 FilterChain으로 넘기면
         Spring Security의 AnonymousAuthenticationFilter가
         미인증 상태를 나타내는 Authentication인 AnonymousAuthenticationToken을 SecurityContext에 넣어준다.
         이후 AuthorizationFilter에서 해당 Authentication은 미인증과 동일하게 처리된다.
         */
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            request.setAttribute("jwt.exception", "TOKEN_NOT_PROVIDED");
            request.setAttribute("jwt.message", "JWT 토큰이 없습니다.");
            filterChain.doFilter(request, response);
            return;
        }

        String jwt = jwtUtil.getTokenFromRequest(request);
        try {
            jwtUtil.validateToken(jwt);
        } catch (JwtAuthenticationException e) {
            request.setAttribute("jwt.exception", e.getErrorCode());
            request.setAttribute("jwt.message", e.getMessage());
            filterChain.doFilter(request, response);
            return;
        }

        String id = jwtUtil.extractUserIdFromToken(jwt);
        String role = jwtUtil.extractUserRoleFromToken(jwt);
        User user = new User(id, "", List.of(new SimpleGrantedAuthority(role)));

        SecurityContextHolder.getContext().setAuthentication(
            new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities()));
        filterChain.doFilter(request, response);
    }
}
