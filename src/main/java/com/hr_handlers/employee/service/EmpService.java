package com.hr_handlers.employee.service;

import com.hr_handlers.employee.repository.EmpRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmpService {

    private final EmpRepository empRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
}
