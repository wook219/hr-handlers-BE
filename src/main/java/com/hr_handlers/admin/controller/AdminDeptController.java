package com.hr_handlers.admin.controller;

import com.hr_handlers.admin.dto.employee.response.AdminDeptResponseDto;
import com.hr_handlers.admin.service.AdminDeptService;
import com.hr_handlers.global.dto.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/dept")
@PreAuthorize("hasRole('ADMIN')")
public class AdminDeptController {

    private final AdminDeptService deptService;

    // 부서 등록
    @PostMapping
    public SuccessResponse<String> createDepartment(@RequestParam("deptName") String deptName) {
        return deptService.createDepartment(deptName);
    }

    // 부서 전체 조회
    @GetMapping
    public SuccessResponse<List<AdminDeptResponseDto>> getAllDepartments() {
        return deptService.getAllDept();
    }

    // 부서 수정
    @PutMapping("/{id}")
    public SuccessResponse<Boolean> updateDepartment(
            @PathVariable("id") Long id,
            @RequestParam("deptName") String deptName) {
        return deptService.updateDepartment(id, deptName);
    }

    // 부서 삭제
    @DeleteMapping("/{id}")
    public SuccessResponse<Boolean> deleteDepartment(@PathVariable("id") Long id){
        return deptService.delete(id);
    }
}