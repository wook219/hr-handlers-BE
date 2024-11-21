package com.hr_handlers.admin.service;

import com.hr_handlers.admin.dto.salary.request.AdminSalaryCreateRequest;
import com.hr_handlers.admin.dto.salary.response.AdminSalaryResponse;
import com.hr_handlers.admin.repository.AdminAdminSalaryRepository;
import com.hr_handlers.employee.entity.Employee;
import com.hr_handlers.employee.repository.EmpRepository;
import com.hr_handlers.global.dto.SuccessResponse;
import com.hr_handlers.global.exception.ErrorCode;
import com.hr_handlers.global.exception.GlobalException;
import com.hr_handlers.salary.entity.Salary;
import com.hr_handlers.vacation.entity.VacationStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminSalaryService {

    private final AdminAdminSalaryRepository adminSalaryRepository;
    private final EmpRepository empRepository;

    public SuccessResponse<List<AdminSalaryResponse>> getAllUserSalary() {
        List<AdminSalaryResponse> adminSalaryRespons = adminSalaryRepository.findAllSalary();
        return SuccessResponse.of(
                "급여 관리 조회 성공",
                adminSalaryRespons);
    }

    public SuccessResponse createSalary(AdminSalaryCreateRequest salaryCreateRequest) {
        Employee employee = empRepository.findById(salaryCreateRequest.getEmployeeId())
                .orElseThrow(() -> new GlobalException(ErrorCode.EMPLOYEE_NOT_FOUND));

        Salary salaryEntity = Salary.builder()
                .employee(employee)
                .basicSalary(salaryCreateRequest.getBasicSalary())
                .deduction(salaryCreateRequest.getDeduction())
                .netSalary(salaryCreateRequest.getNetSalary())
                .payDate(salaryCreateRequest.getPayDate())
                .build();

        adminSalaryRepository.save(salaryEntity);

        return SuccessResponse.of(
                "급여가 등록 되었습니다.",
                null);
    }
}
