package com.hr_handlers.employee.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestDto {

    @NotBlank(message = "사원 번호 입력은 필수입니다.")
    private String empNo;

    @NotBlank(message = "비밀번호 입력은 필수입니다.")
    private String password;
}