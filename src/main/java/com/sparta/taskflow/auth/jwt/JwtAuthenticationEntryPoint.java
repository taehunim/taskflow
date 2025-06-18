package com.sparta.taskflow.auth.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.taskflow.response.ApiResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

/***
 * Spring Security의 AuthorizationFilter와 협력하여
 * 미인증 사용자에게 401 Unauthorized로 응답한다.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
        AuthenticationException authException) throws IOException, ServletException {

        String errorMessage = (String) request.getAttribute("jwt.message");
        if(errorMessage == null) {
            errorMessage = "인증이 필요합니다.";
        }

        ApiResponse apiResponse = ApiResponse.fail(errorMessage, null);

        response.setStatus(401);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(apiResponse));
    }

}
