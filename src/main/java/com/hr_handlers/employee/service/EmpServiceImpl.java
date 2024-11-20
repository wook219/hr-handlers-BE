package com.hr_handlers.employee.service;

import com.hr_handlers.employee.repository.EmpRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmpServiceImpl implements EmpService{

    private final EmpRepository empRepository;
}
