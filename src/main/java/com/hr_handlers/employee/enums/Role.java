package com.hr_handlers.employee.enums;

public enum Role {
    EMPLOYEE("ROLE_EMPLOYEE"), // 사원
    ADMIN("ROLE_ADMIN"),       // 관리자(인사관리팀)
    OWNER("ROLE_OWNER");       // 대표자

    private final String roleName;

    Role(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }
}