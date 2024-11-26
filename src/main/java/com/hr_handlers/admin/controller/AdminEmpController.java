package com.hr_handlers.admin.controller;

import com.hr_handlers.admin.dto.employee.request.EmpRegisterDto;
import com.hr_handlers.admin.dto.employee.request.AdminEmpUpdateRequestDto;
import com.hr_handlers.admin.dto.employee.response.AdminEmpResponseDto;
import com.hr_handlers.admin.service.AdminEmpService;
import com.hr_handlers.global.dto.SuccessResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sortField", defaultValue = "createdAt") String sortField,
            @RequestParam(value = "sortDir", defaultValue = "desc") String sortDir,
            @RequestParam(value = "keyword", required = false) String keyword) {
        return adminEmpService.getAllEmp(page, size, sortField, sortDir, keyword);
    }

    // 사원 수정
    @PatchMapping("/{empNo}")
    public SuccessResponse<Void> updateEmpDetail(@PathVariable("empNo") String empNo,
                                                 @Valid @RequestBody AdminEmpUpdateRequestDto updateRequest){
        return adminEmpService.updateEmpDetail(empNo, updateRequest);
    }

    // 사원 삭제
    @DeleteMapping("/{empNo}")
    public SuccessResponse<Void> deleteEmp(@PathVariable("empNo") String empNo){
        return adminEmpService.delete(empNo);
    }
}