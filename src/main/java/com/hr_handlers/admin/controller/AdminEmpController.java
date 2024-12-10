package com.hr_handlers.admin.controller;

import com.hr_handlers.admin.dto.employee.request.EmpRegisterDto;
import com.hr_handlers.admin.dto.employee.request.AdminEmpUpdateRequestDto;
import com.hr_handlers.global.dto.SearchRequestDto;
import com.hr_handlers.admin.dto.employee.response.AdminEmpResponseDto;
import com.hr_handlers.admin.service.AdminEmpService;
import com.hr_handlers.global.dto.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
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

    @PostMapping
    @Operation(summary = "사원 등록", description = "사원을 등록합니다.")
    public SuccessResponse<String> registerEmp(@Valid @RequestBody EmpRegisterDto registerDto){
        return adminEmpService.register(registerDto);
    }

    @GetMapping
    @Operation(summary = "사원 전체 조회", description = "모든 사원을 조회, 검색합니다.")
    public SuccessResponse<Page<AdminEmpResponseDto>> getAllEmp(
            @RequestParam(defaultValue = "0", value = "page") int page,
            @RequestParam(defaultValue = "10", value = "size") int size,
            @RequestParam(defaultValue = "createdAt", value = "sortField") String sortField,
            @RequestParam(defaultValue = "desc", value = "sortDir") String sortDir,
            @RequestParam(required = false, value = "keyword") String keyword
    ) {
        SearchRequestDto requestDto = new SearchRequestDto(page, size, sortField, sortDir, keyword);
        return adminEmpService.getAllEmp(requestDto);
    }

    @PatchMapping("/{empNo}")
    @Operation(summary = "사원 정보 수정", description = "사원의 직급, 부서, 계약형태를 수정합니다.")
    public SuccessResponse<Boolean> updateEmpDetail(@PathVariable("empNo") String empNo,
                                                 @Valid @RequestBody AdminEmpUpdateRequestDto updateRequest){
        return adminEmpService.updateEmpDetail(empNo, updateRequest);
    }

    @DeleteMapping("/{empNo}")
    @Operation(summary = "사원 삭제", description = "사원을 삭제합니다.")
    public SuccessResponse<Boolean> deleteEmp(@PathVariable("empNo") String empNo){
        return adminEmpService.delete(empNo);
    }

    @GetMapping("/search")
    @Operation(summary = "사원 검색", description = "직급. 부서로 사원을 검색합니다.")
    public SuccessResponse<List<AdminEmpResponseDto>> searchEmp(
            @RequestParam("position") String position,
            @RequestParam("deptName") String deptName
            ) {
        return adminEmpService.searchEmp(position, deptName);
    }
}