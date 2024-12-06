package com.hr_handlers.admin.service;

import com.hr_handlers.admin.dto.employee.response.AdminDeptResponseDto;
import com.hr_handlers.employee.entity.Department;
import com.hr_handlers.employee.mapper.EmpMapper;
import com.hr_handlers.employee.repository.DeptRepository;
import com.hr_handlers.global.dto.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminDeptService {

    private final DeptRepository deptRepository;

    // 부서 등록
    public SuccessResponse<String> createDepartment(@RequestParam String deptName) {
        if (deptRepository.existsByDeptName(deptName)) {
            throw new IllegalArgumentException("이미 존재하는 부서명입니다.");
        }

        Department department = Department.builder()
                .deptName(deptName)
                .build();
        deptRepository.save(department);

        return SuccessResponse.of("부서 등록 성공", "부서가 등록되었습니다.");
    }

    // 부서 전체 조회
    public SuccessResponse<List<AdminDeptResponseDto>> getAllDept() {
        List<Department> departments = deptRepository.findAll();

        List<AdminDeptResponseDto> departmentDtos = departments.stream()
                .map(EmpMapper::toDeptListResponseDto)
                .collect(Collectors.toList());
        return SuccessResponse.of("부서 조회 성공", departmentDtos);
    }

    // 부서 수정
    public SuccessResponse<Boolean> updateDepartment(Long id, String deptName) {
        deptRepository.updateDept(id, deptName);
        return SuccessResponse.of("부서 수정 성공", true);
    }

    // 부서 삭제
    public SuccessResponse<Boolean> delete(Long id) {
        deptRepository.deleteDept(id);
        return SuccessResponse.of("부서 삭제 성공", true);
    }
}