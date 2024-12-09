package com.hr_handlers.employee.service;

import com.hr_handlers.employee.dto.request.EmpUpdateRequestDto;
import com.hr_handlers.employee.dto.response.MailDto;
import com.hr_handlers.employee.dto.response.EmpDetailResponseDto;
import com.hr_handlers.employee.entity.Employee;
import com.hr_handlers.employee.mapper.EmpMapper;
import com.hr_handlers.employee.repository.EmpRepository;
import com.hr_handlers.global.dto.SuccessResponse;
import com.hr_handlers.global.exception.ErrorCode;
import com.hr_handlers.global.exception.GlobalException;
import com.hr_handlers.global.service.S3Service;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class EmpService {

    private final EmpRepository empRepository;
    private final PasswordEncoder passwordEncoder;
    private final S3Service s3Service;
    private final JavaMailSender mailSender;
    private static final String FROM_ADDRESS = "jsjhyun98@gmail.com";

    // 사원 상세 조회
    public SuccessResponse<EmpDetailResponseDto> getEmpDetail(String empNo){
        Employee employee =  empRepository.findByEmpNo(empNo)
                .orElseThrow(() -> new GlobalException(ErrorCode.EMPLOYEE_NOT_FOUND));
        return SuccessResponse.of("사원 상세 조회 성공", EmpMapper.toEmpDetailResponseDto(employee));
    }

    // 사원 수정
    @Transactional
    public SuccessResponse<Boolean> updateEmpDetail(String empNo, EmpUpdateRequestDto requestDto, MultipartFile profileImageFile) throws IOException {
        String profileImageUrl = null;  // 기본값

        // S3에 새 프로필 이미지 업로드
        if (profileImageFile != null && !profileImageFile.isEmpty()) {
            Employee existingEmployee = empRepository.findByEmpNo(empNo)
                    .orElseThrow(() -> new GlobalException(ErrorCode.EMPLOYEE_NOT_FOUND));
            // 기존 프로필 이미지 삭제
            if (existingEmployee.getProfileImage() != null && existingEmployee.getProfileImage().getProfileImageUrl() != null) {
                s3Service.deleteFile(existingEmployee.getProfileImage().getProfileImageUrl());
            }
            profileImageUrl = s3Service.uploadFile("user/profile", profileImageFile);
        }

        empRepository.updateEmp(empNo, requestDto, profileImageUrl);
        return SuccessResponse.of("사원 정보 수정", true);
    }

    // 비밀번호 찾기 : 사원 정보 확인
    public SuccessResponse<Boolean> matchEmailAndEmpNo(String empNo, String email) {
        boolean isMatched = empRepository.existsByEmpNoAndEmail(empNo, email);
        if (!isMatched) {
            throw new GlobalException(ErrorCode.EMPLOYEE_NOT_FOUND);
        }
        return SuccessResponse.of("사원번호 이메일 일치", true);
    }

    // 임시 비밀번호
    public MailDto sendResetPassword(String empNo, String email) {

        // 사원번호와 이메일 확인
        Employee employee = empRepository.findByEmpNoAndEmail(empNo, email)
                .orElseThrow(() -> new GlobalException(ErrorCode.EMPLOYEE_NOT_FOUND));

        // 임시 비밀번호 생성
        String tempPassword = generateTempPassword();
        if (tempPassword.isEmpty()) {
            throw new GlobalException(ErrorCode.TEMP_PASSWORD_GENERATION_FAILED);
        }
        // 비밀번호 업데이트
        String encryptedPassword = passwordEncoder.encode(tempPassword);
        employee.updatePassword(encryptedPassword); // 암호화는 엔티티의 updatePassword 내부에서 처리
        empRepository.save(employee);

        // 메일 데이터 생성
        return MailDto.builder()
                .address(email)
                .title(employee.getName() + "님의 임시 비밀번호 안내 메일입니다.")
                .message("""
                        안녕하세요.

                        임시 비밀번호 안내 메일입니다.

                        임시 비밀번호는 다음과 같습니다:
                        %s

                        로그인 후 반드시 비밀번호를 변경해주세요.
                        """.formatted(tempPassword))
                .build();
    }

    // 메일 전송
    public SuccessResponse<Boolean> sendMail(@Valid MailDto dto) {

        // 메일 전송
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(dto.getAddress().trim());
        message.setFrom(FROM_ADDRESS);
        message.setSubject(dto.getTitle());
        message.setText(dto.getMessage());

        try {
            mailSender.send(message);
        } catch (MailException e) {
            throw new GlobalException(ErrorCode.EMAIL_SEND_FAILED);
        }

        return SuccessResponse.of("메일 전송 성공", true);
    }

    // 임시 비밀번호 생성
    private String generateTempPassword() {
        int length = 10;
        String charPool = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*";
        StringBuilder password = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int randomIndex = (int) (Math.random() * charPool.length());
            password.append(charPool.charAt(randomIndex));
        }
        return password.toString();
    }
//    public SuccessResponse<Boolean> resetPasswordAndSendMail(String empNo, String email) {
//        MailDto mailDto = sendResetPassword(empNo, email); // 임시 비밀번호 생성
//        return sendMail(mailDto); // 이메일 전송
//    }
}