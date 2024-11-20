package com.hr_handlers.employee.controller;

import com.hr_handlers.employee.service.AdminEmpService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminEmpApiController {

    private final AdminEmpService adminEmpService;
}
