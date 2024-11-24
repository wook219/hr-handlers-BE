package com.hr_handlers.admin.controller;

import com.hr_handlers.admin.dto.employee.EmpRegisterDto;
import com.hr_handlers.admin.service.AdminEmpService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/emp")
public class AdminEmpController {

    private final AdminEmpService adminEmpService;

    @PostMapping("/register")
    public ResponseEntity<String> registerEmp(
            @Valid @RequestBody EmpRegisterDto registerDto
    ){
        adminEmpService.register(registerDto);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
