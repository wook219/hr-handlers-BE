package com.hr_handlers.admin.service;

import com.hr_handlers.admin.dto.employee.request.EmpRegisterDto;
import com.hr_handlers.admin.dto.employee.request.AdminEmpUpdateRequestDto;
import com.hr_handlers.admin.dto.employee.response.AdminEmpResponseDto;
import com.hr_handlers.employee.entity.Employee;
import com.hr_handlers.employee.mapper.EmpMapper;
import com.hr_handlers.employee.repository.EmpRepository;
import com.hr_handlers.global.dto.SuccessResponse;
import com.hr_handlers.global.exception.ErrorCode;
import com.hr_handlers.global.exception.GlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminEmpService {

    private final EmpRepository empRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    // 사원 등록
    @Transactional
    public SuccessResponse<String> register(EmpRegisterDto registerRequest){
        if (empRepository.findByEmpNo(registerRequest.getEmpNo()).isPresent()) {
            throw new GlobalException(ErrorCode.EMPLOYEE_ALREADY_EXISTS);
        }
        String encodedPassword = bCryptPasswordEncoder.encode(registerRequest.getPassword());

        Employee newEmployee = EmpMapper.toEmployeeEntity(registerRequest, encodedPassword);

        empRepository.save(newEmployee);

        return SuccessResponse.of("사원 등록 성공", null);
    }

    // 사원 삭제
    @Transactional
    public SuccessResponse<Void> delete(String empNo) {
        Employee employee = findEmployeeByEmpNo(empNo);

        empRepository.delete(employee);

        return SuccessResponse.of("사원 삭제 성공", null);
    }

    // 사원 수정
    @Transactional
    public SuccessResponse<Void> updateEmpDetail(String empNo, AdminEmpUpdateRequestDto updateRequest) {
        Employee employee = findEmployeeByEmpNo(empNo);

        employee.adminUpdate(
                updateRequest.getEmpNo(),
                updateRequest.getContractType(),
                updateRequest.getPosition(),
                updateRequest.getLeaveBalance(),
                updateRequest.getDepartmentName()
        );
        empRepository.save(employee);

        return SuccessResponse.of("사원 정보 수정", null);
    }

    // 사원 전체 조회
    @Transactional(readOnly = true)
    public SuccessResponse<Page<AdminEmpResponseDto>> getAllEmp(
            int page, int size, String sortField, String sortDir, String keyword) {

        Sort sort = Sort.by(sortField);
        sort = sortDir.equalsIgnoreCase("asc") ? sort.ascending() : sort.descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Employee> employees = (keyword != null && !keyword.isEmpty())
                ? empRepository.findEmpByName(keyword, pageable)
                : empRepository.findAll(pageable);

        Page<AdminEmpResponseDto> response = employees.map(EmpMapper::toEmpListResponseDto);

        return SuccessResponse.of("사원 전체 조회 성공", response);
    }

    private Employee findEmployeeByEmpNo(String empNo) {
        return empRepository.findByEmpNo(empNo)
                .orElseThrow(() -> new GlobalException(ErrorCode.EMPLOYEE_NOT_FOUND));
    }
}