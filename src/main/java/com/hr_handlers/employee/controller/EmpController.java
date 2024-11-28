package com.hr_handlers.employee.controller;

import com.hr_handlers.employee.dto.request.EmpUpdateRequestDto;
import com.hr_handlers.employee.dto.response.EmpDetailResponseDto;
import com.hr_handlers.employee.service.EmpService;

import com.hr_handlers.global.dto.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/emp")
public class EmpController {

    private final EmpService empService;

    // 사원 조회
    @GetMapping
    public SuccessResponse<EmpDetailResponseDto> getEmpDetail(Authentication authentication){
        return empService.getEmpDetail(authentication.getName());
    }

    // 사원 수정
    @PatchMapping
    public SuccessResponse<Void> updateEmpDetail(Authentication authentication,
                                                 @RequestBody EmpUpdateRequestDto updateRequest){
        String empNo = authentication.getName();
        return empService.updateEmpDetail(empNo, updateRequest);
    }
}