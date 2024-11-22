package com.hr_handlers.global.security;

import com.hr_handlers.employee.entity.Employee;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@RequiredArgsConstructor
public class UserDetailsImpl implements UserDetails {

    private final Employee employee;

    /**
     * 사용자의 권한 목록을 반환합니다.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<GrantedAuthority> collection = new ArrayList<>();
        collection.add((GrantedAuthority) () -> String.valueOf(employee.getRole()));

        return collection;
    }

    /**
     * 사용자의 암호화된 비밀번호를 반환합니다.
     */
    @Override
    public String getPassword() {
        return employee.getPassword();
    }

    /**
     * 사원번호를 ID로 사용하는 사용자 이름 반환.
     */
    @Override
    public String getUsername() {
        return employee.getEmpNo();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // 계정이 만료되지 않음
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // 계정이 잠겨 있지 않음
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // 비밀번호가 만료되지 않음
    }

    @Override
    public boolean isEnabled() {
        return true; // 계정이 활성화됨
    }
}