package com.hr_handlers.admin.dto.salary.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class AdminSalaryCreateRequest {

    @NotNull(message = "사원 id가 없습니다.")
    private Long employeeId;
    @NotNull(message = "기본급이 없습니다.")
    private Integer basicSalary;
    @NotNull(message = "공제금액이 없습니다.")
    private Integer deduction;
    @NotNull(message = "실수령액이 없습니다.")
    private Integer netSalary;
    @NotNull(message = "급여지급일이 없습니다.")
    private LocalDate payDate;

}
