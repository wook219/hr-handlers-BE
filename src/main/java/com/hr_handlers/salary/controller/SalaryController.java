package com.hr_handlers.salary.controller;


import com.hr_handlers.global.dto.SuccessResponse;
import com.hr_handlers.salary.dto.response.SalaryResponse;
import com.hr_handlers.salary.service.SalaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/salary")
@RequiredArgsConstructor
public class SalaryController {

    private final SalaryService salaryService;

    // 로그인한 유저의 급여 전체조회
    @GetMapping()
    public SuccessResponse<List<SalaryResponse>> getUserSalary() {
        return salaryService.getSalaryByUser();
    }
}
