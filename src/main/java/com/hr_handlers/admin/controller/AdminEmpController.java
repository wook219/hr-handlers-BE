package com.hr_handlers.admin.controller;

import com.hr_handlers.admin.dto.employee.request.EmpRegisterDto;
import com.hr_handlers.admin.dto.employee.request.AdminEmpUpdateRequestDto;
import com.hr_handlers.global.dto.SearchRequestDto;
import com.hr_handlers.admin.dto.employee.response.AdminEmpResponseDto;
import com.hr_handlers.admin.service.AdminEmpService;
import com.hr_handlers.global.dto.SuccessResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/emp")
@PreAuthorize("hasRole('ADMIN')")
public class AdminEmpController {

    private final AdminEmpService adminEmpService;

    // 사원 등록
    @PostMapping
    public SuccessResponse<String> registerEmp(@Valid @RequestBody EmpRegisterDto registerDto){
        return adminEmpService.register(registerDto);
    }

    // 사원 전체 조회
    @GetMapping
    public SuccessResponse<Page<AdminEmpResponseDto>> getAllEmp(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortField,
            @RequestParam(defaultValue = "desc") String sortDir,
            @RequestParam(required = false) String keyword
    ) {
        SearchRequestDto requestDto = new SearchRequestDto(page, size, sortField, sortDir, keyword);
        return adminEmpService.getAllEmp(requestDto);
    }

    // 사원 수정
    @PatchMapping("/{empNo}")
    public SuccessResponse<Boolean> updateEmpDetail(@PathVariable("empNo") String empNo,
                                                 @Valid @RequestBody AdminEmpUpdateRequestDto updateRequest){
        return adminEmpService.updateEmpDetail(empNo, updateRequest);
    }

    // 사원 삭제
    @DeleteMapping("/{empNo}")
    public SuccessResponse<Boolean> deleteEmp(@PathVariable("empNo") String empNo){
        return adminEmpService.delete(empNo);
    }

    // 직급.부서로 사원 검색
    @GetMapping("/search")
    public SuccessResponse<List<AdminEmpResponseDto>> searchEmp(
            @RequestParam("position") String position,
            @RequestParam("deptName") String deptName
            ) {
        return adminEmpService.searchEmp(position, deptName);
    }
}