package com.hr_handlers.admin.service;

import com.hr_handlers.admin.dto.salary.request.*;
import com.hr_handlers.admin.dto.salary.response.AdminSalaryResponseDto;
import com.hr_handlers.admin.repository.AdminSalaryRepository;
import com.hr_handlers.employee.entity.Employee;
import com.hr_handlers.employee.repository.EmpRepository;
import com.hr_handlers.global.dto.SuccessResponse;
import com.hr_handlers.global.exception.ErrorCode;
import com.hr_handlers.global.exception.GlobalException;
import com.hr_handlers.global.utils.ExcelUploadUtils;
import com.hr_handlers.salary.entity.Salary;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminSalaryService {

    private final AdminSalaryRepository adminSalaryRepository;
    private final EmpRepository empRepository;
    private final ExcelUploadUtils excelUploadUtils;

    // 급여관리 전체 조회
    public SuccessResponse<List<AdminSalaryResponseDto>> getAllUserSalary() {
        return SuccessResponse.of("급여 관리 조회 성공", adminSalaryRepository.findAllSalary());
    }

    // 급여관리 조건 조회
    public SuccessResponse<Page<AdminSalaryResponseDto>> searchSalary(Pageable pageable, AdminSalarySearchRequestDto adminSalarySearchRequestDto) {
        return SuccessResponse.of("급여 관리 조회 성공", adminSalaryRepository.searchSalaryByFilter(pageable, adminSalarySearchRequestDto));
    }

    // 급여관리 추가
    public SuccessResponse<Boolean> createSalary(AdminSalaryCreateRequestDto salaryCreateRequest) {
        Employee employee = empRepository.findById(salaryCreateRequest.getEmployeeId()).orElseThrow(() -> new GlobalException(ErrorCode.EMPLOYEE_NOT_FOUND));
        Salary salaryEntity = salaryCreateRequest.toCreateEntity(employee);
        adminSalaryRepository.save(salaryEntity);
        return SuccessResponse.of("급여가 등록 되었습니다.", true);
    }

    // 급여관리 수정
    @Transactional
    public SuccessResponse<Boolean> updateSalary(AdminSalaryUpdateRequestDto adminSalaryUpdateRequestDto) {
        Salary salaryEntity = adminSalaryRepository.findById(adminSalaryUpdateRequestDto.getSalaryId()).orElseThrow(() -> new GlobalException(ErrorCode.SALARY_NOT_FOUND));
        adminSalaryUpdateRequestDto.toUpdateEntity(salaryEntity);
        return SuccessResponse.of("급여가 수정 되었습니다.", true);
    }

    // 급여관리 삭제
    public SuccessResponse<Boolean> deleteSalary(List<Long> salaryIds) {
        adminSalaryRepository.deleteAllByIdInBatch(salaryIds);
        return SuccessResponse.of("급여가 삭제 되었습니다.", true);
    }

    // 급여관리 엑셀 업로드
    @Transactional
    public SuccessResponse<Boolean> excelUploadSalary(List<AdminSalaryExcelUploadRequestDto> adminSalaryExcelUploadRequestDtos) {

        // todo : 엑셀dto에서 a사원의 2024-11-13 row가 2개 이상일 경우 유효성 검사 어떻게 처리할지??
        // todo : 엑셀에 빈 행이 있을경우 유효성 검사??

        // 엑셀 dto에서 Employee ID 목록 뽑아내기
        List<Long> employeeIds = adminSalaryExcelUploadRequestDtos.stream()
                .map(AdminSalaryExcelUploadRequestDto::getEmployeeId)
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

        List<Salary> salaries = adminSalaryExcelUploadRequestDtos.stream()
                .map(request -> {
                    Employee employee = Optional.ofNullable(employeeMap.get(request.getEmployeeId()))
                            .orElseThrow(() -> new GlobalException(ErrorCode.EMPLOYEE_NOT_FOUND));
                    return request.toCreateEntity(employee);
                })
                .collect(Collectors.toList());

        adminSalaryRepository.saveAll(salaries);

        return SuccessResponse.of("급여가 등록 되었습니다.", true);
    }

    public SuccessResponse<Boolean> excelDownloadSalary(OutputStream stream, AdminSalarySearchRequestDto adminSalarySearchRequestDto) throws IOException, IllegalAccessException {
        List<AdminSalaryExcelDownloadRequestDto> adminSalaryResponseDtos = adminSalaryRepository.searchSalaryForExcel(adminSalarySearchRequestDto);
        excelUploadUtils.renderObjectToExcel(stream, adminSalaryResponseDtos, AdminSalaryExcelDownloadRequestDto.class);
        return SuccessResponse.of("성공적으로 다운로드 되었습니다.", true);
    }
}
