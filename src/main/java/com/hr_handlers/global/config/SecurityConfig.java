package com.hr_handlers.global.config;

import com.hr_handlers.global.jwt.JwtAuthenticationFilter;
import com.hr_handlers.global.jwt.JwtAuthorizationFilter;
import com.hr_handlers.global.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
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
import org.springframework.web.cors.CorsConfiguration;

import java.util.Collections;
import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthenticationConfiguration authenticationConfiguration;
    private final JwtUtil jwtUtil;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    // 비밀번호 암호화
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {

        return new BCryptPasswordEncoder();
    }

    // 세션 설정
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf((csrf) -> csrf.disable())
                .cors((cors) -> cors
                        .configurationSource(request -> {
                            CorsConfiguration configuration = new CorsConfiguration();
                            configuration.setAllowedOrigins(List.of(
                                    "http://localhost:3000",
                                    "http://34.47.90.224:8080",
                                    "http://34.47.90.224:3000"));
                            configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE"));
                            configuration.setAllowCredentials(true);
                            configuration.setAllowedHeaders(Collections.singletonList("*"));
                            configuration.setMaxAge(3600L);
                            configuration.setExposedHeaders(List.of("access", "Content-Disposition"));
                            return configuration;
                        }))
                .formLogin((auth) -> auth.disable())
                .httpBasic((auth) -> auth.disable());

        // 경로별 접근 제어
        http.authorizeHttpRequests((request) ->
                request
                        // 전체 접근
                        .requestMatchers("/login", "/reissue","/api/s3/**").permitAll()

                        // Swagger 경로 전체 접근
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html", "/swagger-resources/**").permitAll()

                        // 관리자만 접근
                        .requestMatchers("/admin/**").hasRole("ADMIN")

                        /* 사원 */
                        .requestMatchers("/emp/**").hasAnyRole("ADMIN", "EMPLOYEE")

                        /* 채팅 */
                        .requestMatchers("/chat/**").hasAnyRole("ADMIN", "EMPLOYEE")
                        .requestMatchers("/chatroom/**").hasAnyRole("ADMIN", "EMPLOYEE")
                        .requestMatchers("/message/**").hasAnyRole("ADMIN", "EMPLOYEE")

                        /* 휴가 */

                        /* 게시판 */
                        .requestMatchers("/post/**", "/comment/**").hasAnyRole("ADMIN", "EMPLOYEE")



                        // .anyRequest().permitAll());          // 전체 허용(임시)
                        .anyRequest().authenticated());
        http
                .addFilterBefore(new JwtAuthorizationFilter(jwtUtil), JwtAuthenticationFilter.class);

        http
                .addFilterAt(new JwtAuthenticationFilter(authenticationManager(authenticationConfiguration), jwtUtil), UsernamePasswordAuthenticationFilter.class);

        // 세션 관리: Stateless 모드 설정
        http.sessionManagement((sm) -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }
}