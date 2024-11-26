package com.hr_handlers.global.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hr_handlers.employee.dto.request.LoginRequestDto;
import com.hr_handlers.global.exception.ErrorCode;
import com.hr_handlers.global.exception.GlobalException;
import com.hr_handlers.global.security.UserDetailsImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    // 로그인 요청 처리
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            LoginRequestDto loginRequestDto = objectMapper.readValue(request.getInputStream(), LoginRequestDto.class);

            if (loginRequestDto.getEmpNo() == null || loginRequestDto.getEmpNo().isEmpty()) {
                throw new GlobalException(ErrorCode.INVALID_LOGIN_REQUEST);
            }

            String empNo = loginRequestDto.getEmpNo();
            String password = loginRequestDto.getPassword();

            // 토큰 생성 인증 요청
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(empNo, password, null);
            return authenticationManager.authenticate(authToken);
        } catch (IOException e) {
            throw new GlobalException(ErrorCode.INVALID_LOGIN_REQUEST);
        }
    }

    // 인증 성공 처리 : JWT 토큰 생성
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) {
        log.info("로그인 성공, Jwt 발급 시작");

        // 인증 사용자 가져오기
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        String empNo = userDetails.getUsername();

        // 권한 정보 추출
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();

        String role = auth.getAuthority();

        // 토큰 생성
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