package com.hr_handlers.admin.service;

import com.hr_handlers.admin.dto.employee.request.EmpRegisterDto;
import com.hr_handlers.admin.dto.employee.request.AdminEmpUpdateRequestDto;
import com.hr_handlers.global.dto.SearchRequestDto;
import com.hr_handlers.admin.dto.employee.response.AdminEmpResponseDto;
import com.hr_handlers.admin.repository.employee.AdminEmpRepository;
import com.hr_handlers.employee.entity.Department;
import com.hr_handlers.employee.entity.Employee;
import com.hr_handlers.employee.mapper.EmpMapper;
import com.hr_handlers.employee.repository.DeptRepository;
import com.hr_handlers.global.dto.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminEmpService {

    private final AdminEmpRepository empRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final DeptRepository deptRepository;

    // 사원 등록
    public SuccessResponse<String> register(EmpRegisterDto registerRequest){

        Department department = deptRepository.findByDeptName(registerRequest.getDeptName())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 부서입니다."));

        empRepository.save(EmpMapper.toEmployeeEntity(registerRequest,
                bCryptPasswordEncoder.encode(registerRequest.getPassword()),
                department));

        return SuccessResponse.of("사원 등록 성공", "사원이 등록되었습니다.");
    }

    // 사원 삭제
    public SuccessResponse<Boolean> delete(String empNo) {
        empRepository.deleteEmp(empNo);
        return SuccessResponse.of("사원 삭제 성공", true);
    }

    // 사원 수정
    public SuccessResponse<Boolean> updateEmpDetail(String empNo, AdminEmpUpdateRequestDto updateRequest) {
        empRepository.updateEmp(empNo, updateRequest);
        return SuccessResponse.of("사원 정보 수정", true);
    }

    // 사원 전체 조회
    public SuccessResponse<Page<AdminEmpResponseDto>> getAllEmp(SearchRequestDto requestDto) {
        // 검색 조건에 따른 데이터 조회
        Page<Employee> employees = empRepository.findEmpByName(requestDto);

        return SuccessResponse.of("사원 전체 조회 성공", employees.map(EmpMapper::toEmpListResponseDto));
    }

    public SuccessResponse<List<AdminEmpResponseDto>> searchEmp(String position, String deptName) {
        List<Employee> employeeEntity = empRepository.findByPositionAndDepartmentDeptName(position, deptName);
        List<AdminEmpResponseDto> employeeResponse  = employeeEntity.stream()
                .map(EmpMapper::toEmpListResponseDto)
                .toList();
        return SuccessResponse.of("사원 조회 성공", employeeResponse);
    }
}