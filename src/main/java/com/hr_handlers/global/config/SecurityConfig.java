package com.hr_handlers.global.config;

import com.hr_handlers.global.jwt.JwtAuthenticationFilter;
import com.hr_handlers.global.jwt.JwtAuthorizationFilter;
import com.hr_handlers.global.jwt.JwtUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final AuthenticationConfiguration authenticationConfiguration;
    private final JwtUtil jwtUtil;

    public SecurityConfig(AuthenticationConfiguration authenticationConfiguration, JwtUtil jwtUtil){
        this.authenticationConfiguration = authenticationConfiguration;
        this.jwtUtil = jwtUtil;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    // 비밀번호 암호화
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {

        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        // CSRF 보호 비활성화 (JWT를 사용하므로 세션 기반 보호가 필요 없음)
        http
                .csrf((csrf) -> csrf.disable());

        // Form Login 비활성화 (JWT 기반 인증 사용)
        http
                .formLogin((auth) -> auth.disable());

        // http basic 인증 방식 비활성화 (JWT를 사용하므로 필요 없음)
        http
                .httpBasic((auth) -> auth.disable());

        // 경로별 접근 제어 설정
        http.authorizeHttpRequests((request) ->
                request
                        // 로그인 경로 전체 접근 허용
                        .requestMatchers("/login").permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        // Swagger 문서 관련 경로 전체 접근 허용
                        // .requestMatchers("/swagger-ui/**").permitAll()
                        // .anyRequest().permitAll());          // 전체 허용(임시)
                        .anyRequest().authenticated());   // 인증 필요

        http
                .addFilterBefore(new JwtAuthorizationFilter(jwtUtil), JwtAuthenticationFilter.class);

        http
                .addFilterAt(new JwtAuthenticationFilter(authenticationManager(authenticationConfiguration), jwtUtil), UsernamePasswordAuthenticationFilter.class);

        // 세션 관리: Stateless 모드 설정 (JWT 사용 시 세션을 생성하지 않음)
        http.sessionManagement((sm) -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }
}