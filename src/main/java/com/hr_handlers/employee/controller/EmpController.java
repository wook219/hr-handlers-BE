package com.hr_handlers.employee.controller;

import com.hr_handlers.employee.dto.request.EmpUpdateRequestDto;
import com.hr_handlers.employee.dto.response.EmpDetailResponseDto;
import com.hr_handlers.employee.service.EmpService;

import com.hr_handlers.global.dto.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/emp")
public class EmpController {

    private final EmpService empService;

    // 사원 조회
    @GetMapping("/{empNo}")
    public SuccessResponse<EmpDetailResponseDto> getEmpDetail(@PathVariable("empNo") String empNo){
        return empService.getEmpDetail(empNo);
    }

    // 사원 수정
    @PatchMapping("/{empNo}")
    public SuccessResponse<Void> modifyEmpDetail(@PathVariable("empNo") String empNo,
                                                 @RequestBody EmpUpdateRequestDto updateRequest){
        return empService.updateEmpDetail(empNo, updateRequest);
    }
}