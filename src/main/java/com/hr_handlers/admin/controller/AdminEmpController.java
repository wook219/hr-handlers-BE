package com.hr_handlers.admin.controller;

import com.hr_handlers.admin.dto.employee.EmpRegisterDto;
import com.hr_handlers.admin.service.AdminEmpService;
import com.hr_handlers.global.dto.SuccessResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/emp")
public class AdminEmpController {

    private final AdminEmpService adminEmpService;

    // 사원 등록
    @PostMapping
    public SuccessResponse<String> registerEmp(@Valid @RequestBody EmpRegisterDto registerDto){
        return adminEmpService.register(registerDto);
    }

    // 사원 조회


    // 사원 전체 조회


}
