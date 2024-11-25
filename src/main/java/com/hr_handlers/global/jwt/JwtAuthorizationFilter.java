package com.hr_handlers.global.jwt;

import com.hr_handlers.employee.entity.Employee;
import com.hr_handlers.employee.enums.Role;
import com.hr_handlers.global.security.UserDetailsImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

// JWT 인가 필터
@Slf4j(topic = "인가")
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Authorization 헤더에서 JWT를 추출
        String authorization = request.getHeader("Authorization");

        if(authorization == null || !authorization.startsWith("Bearer ")){
            log.info("token null");
            filterChain.doFilter(request, response);

            return;
        }

        log.info("authorization now");

        String token = authorization.split(" ")[1];

        if(jwtUtil.isExpired(token)){
            log.info("token expired");
            filterChain.doFilter(request, response);

            return;
        }

        String empNo = jwtUtil.getUsername(token);
        String role = jwtUtil.getRole(token);

        Employee employee = Employee.builder()
                .empNo(empNo)
                .password("temppassword")
                .role(Role.valueOf(role))
                .build();

        UserDetailsImpl userDetails = new UserDetailsImpl(employee);

        Authentication authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);
    }
}