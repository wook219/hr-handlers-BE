package com.hr_handlers.admin.dto.employee.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AdminEmpUpdateRequestDto {

    @NotBlank(message = "사원 번호는 필수입니다.")
    private String empNo;

    @NotBlank(message = "직급은 필수입니다.")
    private String position;

    private String contractType;

    @Size(min = 0, max = 20, message = "휴가 잔여일수는 0 이상이어야 합니다.")
    private Double leaveBalance;

    private String deptName;
}
