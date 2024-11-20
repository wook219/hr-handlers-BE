package com.hr_handlers.employee.controller;

import com.hr_handlers.employee.service.EmpService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class EmpApiController {

    private final EmpService empService;
}
