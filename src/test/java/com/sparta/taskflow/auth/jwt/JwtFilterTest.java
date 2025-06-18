package com.sparta.taskflow.auth.jwt;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.willDoNothing;

import com.sparta.taskflow.domain.user.type.RoleType;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import java.io.IOException;
import java.security.Key;
import java.util.Base64;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class JwtFilterTest {

    JwtUtil jwtUtil = new JwtUtil();
    JwtFilter jwtFilter = new JwtFilter(jwtUtil); // JwtUtil과 강하게 결합되어 분리 어려움

    @Mock
    FilterChain filterChain;

    @BeforeEach
    void setKey() {
        String secretKey = "slkdfjsdvhzxuocgvoawgwiufbdjkszvbkxsdfiusydv";
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        Key key = Keys.hmacShaKeyFor(bytes);
        ReflectionTestUtils.setField(jwtUtil, "key", key);
    }

    @AfterEach
    void clearSecurityContext() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void 정상_토큰() throws ServletException, IOException {
        // given
        String token = jwtUtil.createToken(1L, RoleType.USER);

        MockHttpServletRequest httpServletRequest = new MockHttpServletRequest();
        MockHttpServletResponse httpServletResponse = new MockHttpServletResponse();
        httpServletRequest.addHeader("Authorization", "Bearer " + token);
        willDoNothing().given(filterChain).doFilter(httpServletRequest, httpServletResponse);

        // when
        jwtFilter.doFilterInternal(httpServletRequest, httpServletResponse, filterChain);

        // then
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        assertThat(authentication.getPrincipal()).isInstanceOf(User.class);
    }

    @Test
    void 잘못된_토큰() throws ServletException, IOException {
        // given
        MockHttpServletRequest httpServletRequest = new MockHttpServletRequest();
        MockHttpServletResponse httpServletResponse = new MockHttpServletResponse();
        httpServletRequest.addHeader("Authorization", "Bearer INVALID_JWT");
        willDoNothing().given(filterChain).doFilter(httpServletRequest, httpServletResponse);

        // when
        jwtFilter.doFilterInternal(httpServletRequest, httpServletResponse, filterChain);

        // then
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        assertThat(authentication).isNull();

        String message = (String) httpServletRequest.getAttribute("jwt.message");
        assertThat(message).isEqualTo("잘못된 JWT 토큰입니다.");
    }

    @Test
    void 토큰_미제공() throws ServletException, IOException {
        // given
        MockHttpServletRequest httpServletRequest = new MockHttpServletRequest();
        MockHttpServletResponse httpServletResponse = new MockHttpServletResponse();
        willDoNothing().given(filterChain).doFilter(httpServletRequest, httpServletResponse);

        // when
        jwtFilter.doFilterInternal(httpServletRequest, httpServletResponse, filterChain);

        // then
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        assertThat(authentication).isNull();

        String message = (String) httpServletRequest.getAttribute("jwt.message");
        assertThat(message).isEqualTo("JWT 토큰이 없습니다.");
    }
}