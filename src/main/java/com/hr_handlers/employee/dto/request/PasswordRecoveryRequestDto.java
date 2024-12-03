package com.hr_handlers.employee.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PasswordRecoveryRequestDto {

    @NotBlank(message = "사원번호 입력은 필수입니다.")
    private String empNo;

    @NotBlank(message = "이메일 입력은 필수입니다.")
    private String email;
}
