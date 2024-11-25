package com.hr_handlers.global.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hr_handlers.employee.dto.request.LoginRequestDto;
import com.hr_handlers.global.security.UserDetailsImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

// JWT 인증 필터
@Slf4j(topic = "인증 및 Jwt 발급")
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, JwtUtil jwtUtil){
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    // 로그인 요청 처리
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            // JSON 데이터 읽기
            ObjectMapper objectMapper = new ObjectMapper();
            LoginRequestDto loginRequestDto = objectMapper.readValue(request.getInputStream(), LoginRequestDto.class);

            // empNo 값이 null인지 확인
            if (loginRequestDto.getEmpNo() == null || loginRequestDto.getEmpNo().isEmpty()) {
                throw new IllegalArgumentException("empNo 값이 없습니다.");
            }

            // empNo와 password 추출
            String empNo = loginRequestDto.getEmpNo();
            String password = loginRequestDto.getPassword();

            // 토큰 생성 및 인증 요청
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(empNo, password, null);
            return authenticationManager.authenticate(authToken);
        } catch (IOException e) {
            throw new RuntimeException("Failed to parse login request", e);
        }
    }

    // 인증 성공 처리 -> JWT를 생성하여 클라이언트에 반환
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) {
        log.info("로그인 성공, Jwt 발급 시작");
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        String empNo = userDetails.getUsername();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();

        String role = auth.getAuthority();
        String token = jwtUtil.createToken(empNo, role, 10*60*60*1000L); // 시간 설정 필요

        response.addHeader("Authorization", "Bearer " +token);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {
        // 실패 처리 로직
        log.info("로그인 실패");
        response.setStatus(401);
    }
}