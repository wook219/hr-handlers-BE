package com.hr_handlers.admin.service;

import com.hr_handlers.admin.dto.salary.request.AdminSalaryCreateRequest;
import com.hr_handlers.admin.dto.salary.request.AdminSalaryExcelUploadRequest;
import com.hr_handlers.admin.dto.salary.response.AdminSalaryResponse;
import com.hr_handlers.admin.repository.AdminSalaryRepository;
import com.hr_handlers.employee.entity.Employee;
import com.hr_handlers.employee.repository.EmpRepository;
import com.hr_handlers.global.dto.SuccessResponse;
import com.hr_handlers.global.exception.ErrorCode;
import com.hr_handlers.global.exception.GlobalException;
import com.hr_handlers.salary.entity.Salary;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminSalaryService {

    private final AdminSalaryRepository adminSalaryRepository;
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

    public SuccessResponse deleteSalary(List<Long> salaryIds) {
        adminSalaryRepository.deleteAllByIdInBatch(salaryIds);
        return SuccessResponse.of(
                "급여가 삭제 되었습니다.",
                null);
    }

    @Transactional
    public SuccessResponse excelUploadSalary(List<AdminSalaryExcelUploadRequest> adminSalaryExcelUploadRequests) {

        // todo : 엑셀dto에서 a사원의 2024-11-13 row가 2개 이상일 경우 유효성 검사 어떻게 처리할지??
        // todo : 엑셀에 빈 행이 있을경우 유효성 검사??
        // todo : 이미 DB에 a사원의 2024-11-13 데이터가 있을때 엑셀에도 동일한 급여 지급일에 데이터가 있을경우
        //        -> 사용자의 의도 일수있으니 insert한다??
        //        -> 예외 처리한다??

        // 엑셀 dto에서 Employee ID 목록 뽑아내기
        List<Long> employeeIds = adminSalaryExcelUploadRequests.stream()
                .map(AdminSalaryExcelUploadRequest::getEmployeeId)
                .distinct()  // 중복되는 Employee ID를 제외
                .collect(Collectors.toList());

        // Employee ID 목록을 받아서 한 번에 Employee 객체들을 조회
        Map<Long, Employee> employeeMap = empRepository.findAllById(employeeIds).stream()
                .collect(Collectors.toMap(Employee::getId, e -> e));

        // todo :
//        select
//                *
//                from table
//        where employee in (select * from employee where id = 1)

        List<Salary> salaries = adminSalaryExcelUploadRequests.stream()
                .map(request -> {
                    Employee employee = employeeMap.get(request.getEmployeeId());
                    if (employee == null) {
                        // todo: exception 따로처리
                        throw new RuntimeException("Employee not found");
                    }
                    return Salary.builder()
                            .employee(employee)
                            .basicSalary(request.getBasicSalary())
                            .deduction(request.getDeduction())
                            .netSalary(request.getNetSalary())
                            .payDate(request.getPayDate())
                            .build();
                })
                .collect(Collectors.toList());

        adminSalaryRepository.saveAll(salaries);

        return SuccessResponse.of(
                "급여가 등록 되었습니다.",
                null);
    }
}
