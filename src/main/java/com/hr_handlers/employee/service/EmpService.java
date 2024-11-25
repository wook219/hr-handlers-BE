package com.hr_handlers.employee.service;

import com.hr_handlers.employee.dto.request.EmpUpdateRequestDto;
import com.hr_handlers.employee.dto.response.EmpDetailResponseDto;
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
public class EmpService {

    private final EmpRepository empRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public SuccessResponse<EmpDetailResponseDto> getEmpDetail(String empNo){
        Employee employee = empRepository.findByEmpNo(empNo)
                .orElseThrow(() -> new GlobalException(ErrorCode.EMPLOYEE_NOT_FOUND));

        EmpDetailResponseDto response = EmpMapper.toEmpDetailResponseDto(employee);

        return SuccessResponse.of("사원 상세 조회 성공", response);
    }

    public SuccessResponse<Void> updateEmpDetail(String empNo, EmpUpdateRequestDto updateRequest) {
        Employee employee = empRepository.findByEmpNo(empNo)
                .orElseThrow(() -> new GlobalException(ErrorCode.EMPLOYEE_NOT_FOUND));

        employee.update(
                updateRequest.getEmail(),
                updateRequest.getPhone(),
                updateRequest.getIntroduction(),
                updateRequest.getProfileImageUrl()
        );
        empRepository.save(employee);

        return SuccessResponse.of("사원 정보 수정", null);
    }
}
