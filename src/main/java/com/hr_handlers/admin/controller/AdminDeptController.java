package com.hr_handlers.admin.controller;

import com.hr_handlers.admin.dto.employee.response.AdminDeptResponseDto;
import com.hr_handlers.admin.service.AdminDeptService;
import com.hr_handlers.global.dto.SearchRequestDto;
import com.hr_handlers.global.dto.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/dept")
@PreAuthorize("hasRole('ADMIN')")
public class AdminDeptController {

    private final AdminDeptService deptService;

    @PostMapping
    @Operation(summary = "부서 등록", description = "부서를 등록합니다.")
    public SuccessResponse<String> createDepartment(@RequestParam("deptName") String deptName) {
        return deptService.createDepartment(deptName);
    }

    @GetMapping
    @Operation(summary = "부서 전체 조회", description = "모든 부서를 조회합니다.")
    public SuccessResponse<Page<AdminDeptResponseDto>> getAllDepartments(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortField,
            @RequestParam(defaultValue = "desc") String sortDir,
            @RequestParam(required = false) String keyword
    ) {
        SearchRequestDto requestDto = new SearchRequestDto(page, size, sortField, sortDir, keyword);
        return deptService.getAllDept(requestDto);
    }

    @PutMapping("/{id}")
    @Operation(summary = "부서 정보 수정", description = "부서의 이름을 수정합니다.")
    public SuccessResponse<Boolean> updateDepartment(
            @PathVariable("id") Long id,
            @RequestParam("deptName") String deptName) {
        return deptService.updateDepartment(id, deptName);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "부서 삭제", description = "부서를 삭제합니다.")
    public SuccessResponse<Boolean> deleteDepartment(@PathVariable("id") Long id){
        return deptService.delete(id);
    }
}