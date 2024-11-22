package com.hr_handlers.employee.controller;

import com.hr_handlers.employee.service.EmpService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/emp")
public class EmpController {

    private final EmpService empService;

}