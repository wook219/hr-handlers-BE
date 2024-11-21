package com.hr_handlers.admin.controller;


import com.hr_handlers.admin.dto.salary.request.AdminSalaryCreateRequest;
import com.hr_handlers.admin.dto.salary.response.AdminSalaryResponse;
import com.hr_handlers.admin.service.AdminSalaryService;
import com.hr_handlers.global.dto.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/salary")
@RequiredArgsConstructor
public class AdminSalaryController {

    private final AdminSalaryService adminSalaryService;

    // 모든 유저의 급여 전체 조회
    @GetMapping()
    public SuccessResponse<List<AdminSalaryResponse>> getAllUserSalary() {
        return adminSalaryService.getAllUserSalary();
    }

    @PostMapping()
    public SuccessResponse createSalary(@RequestBody @Validated AdminSalaryCreateRequest salaryCreateRequest) {
        return adminSalaryService.createSalary(salaryCreateRequest);
    }
}
