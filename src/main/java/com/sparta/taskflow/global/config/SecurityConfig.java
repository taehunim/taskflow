package com.sparta.taskflow.global.config;

import com.sparta.taskflow.auth.jwt.JwtAuthenticationEntryPoint;
import com.sparta.taskflow.auth.jwt.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

    private final JwtFilter jwtFilter;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
            .formLogin(AbstractHttpConfigurer::disable)
            .cors(Customizer.withDefaults())
            .exceptionHandling(ex -> {
                ex.authenticationEntryPoint(jwtAuthenticationEntryPoint);
            })
            .authorizeHttpRequests(auth -> {
                auth
//                    .requestMatchers("/api/auth/login", "api/auth/register").permitAll()
//                    .anyRequest().authenticated()
                    .anyRequest().permitAll(); // Todo: 개발 환경에서 인증 사용 x, 실제 동작 시 위 주석 해제
            })
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
