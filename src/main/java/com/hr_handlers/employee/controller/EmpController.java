package com.hr_handlers.employee.controller;

import com.hr_handlers.employee.dto.request.EmpUpdateRequestDto;
import com.hr_handlers.employee.dto.request.PasswordRecoveryRequestDto;
import com.hr_handlers.employee.dto.response.EmpDetailResponseDto;
import com.hr_handlers.employee.service.EmpService;

import com.hr_handlers.global.dto.SuccessResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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
    public SuccessResponse<Boolean> updateEmpDetail(
            Authentication authentication,
            @RequestPart("updateRequest") EmpUpdateRequestDto updateRequest,
            @RequestPart(value = "profileImage", required = false) MultipartFile profileImageFile) throws IOException {
        return empService.updateEmpDetail(authentication.getName(), updateRequest, profileImageFile);
    }

    // 비밀번호 찾기
    @PostMapping("/check")
    public SuccessResponse<Boolean> matchEmailAndEmpNo(@Valid @RequestBody PasswordRecoveryRequestDto requestDto){
                return empService.matchEmailAndEmpNo(requestDto.getEmpNo(), requestDto.getEmail());
    }

    // 메일 전송
    @PostMapping("/send/mail")
    public SuccessResponse<Boolean> sendResetPassword(@Valid @RequestBody PasswordRecoveryRequestDto requestDto){
        return empService.sendMail(empService.sendResetPassword(requestDto.getEmpNo(), requestDto.getEmail()));
    }
}