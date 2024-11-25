package com.hr_handlers.admin.service;

import com.hr_handlers.admin.dto.employee.EmpRegisterDto;
import com.hr_handlers.employee.entity.Employee;
import com.hr_handlers.employee.mapper.EmpMapper;
import com.hr_handlers.employee.repository.EmpRepository;
import com.hr_handlers.global.dto.SuccessResponse;
import com.hr_handlers.global.exception.ErrorCode;
import com.hr_handlers.global.exception.GlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminEmpService {

    private final EmpRepository empRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public SuccessResponse<String> register(EmpRegisterDto registerRequest){
        // 사원 번호 중복 체크
        if (empRepository.findByEmpNo(registerRequest.getEmpNo()).isPresent()) {
            throw new GlobalException(ErrorCode.EMPLOYEE_ALREADY_EXISTS);
        }
        // 비밀번호 암호화
        String encodedPassword = bCryptPasswordEncoder.encode(registerRequest.getPassword());

        Employee newEmployee = EmpMapper.toEmployeeEntity(registerRequest, encodedPassword);

        empRepository.save(newEmployee);

        return SuccessResponse.of("사원 등록 성공", "보낼 데이터 없음");
    }

    public SuccessResponse<Void> delete(String empNo) {
        Employee employee = empRepository.findByEmpNo(empNo)
                .orElseThrow(() -> new GlobalException(ErrorCode.EMPLOYEE_NOT_FOUND));
        empRepository.delete(employee);
        return SuccessResponse.of("사원 삭제 성공", null);
    }
}
