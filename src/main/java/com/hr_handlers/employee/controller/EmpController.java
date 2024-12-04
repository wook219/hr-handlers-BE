package com.hr_handlers.employee.controller;

import com.hr_handlers.employee.dto.request.EmpUpdateRequestDto;
import com.hr_handlers.employee.dto.response.MailDto;
import com.hr_handlers.employee.dto.request.PasswordRecoveryRequestDto;
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
    public SuccessResponse<Boolean> updateEmpDetail(Authentication authentication,
                                                 @RequestBody EmpUpdateRequestDto updateRequest){
        return empService.updateEmpDetail(authentication.getName(), updateRequest);
    }

    // 비밀번호 찾기
    @PostMapping("/check")
    public SuccessResponse<Boolean> matchEmailAndEmpNo(@RequestBody PasswordRecoveryRequestDto requestDto){
                return empService.matchEmailAndEmpNo(requestDto.getEmpNo(), requestDto.getEmail());
    }

    // 메일 전송
    @PostMapping("/send/mail")
    public SuccessResponse<Boolean> sendResetPassword(@RequestBody PasswordRecoveryRequestDto requestDto) {
        return empService.resetPasswordAndSendMail(requestDto.getEmpNo(), requestDto.getEmail());
    }
}