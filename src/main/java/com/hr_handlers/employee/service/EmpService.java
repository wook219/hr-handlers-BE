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
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class EmpService {

    private final EmpRepository empRepository;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender mailSender;
    private static final String FROM_ADDRESS = "hth130598@gmail.com";


    // 사원 상세 조회
    public SuccessResponse<EmpDetailResponseDto> getEmpDetail(String empNo){
        Employee employee =  empRepository.findByEmpNo(empNo)
                .orElseThrow(() -> new GlobalException(ErrorCode.EMPLOYEE_NOT_FOUND));
        return SuccessResponse.of("사원 상세 조회 성공", EmpMapper.toEmpDetailResponseDto(employee));
    }

    // 사원 수정
    public SuccessResponse<Boolean> updateEmpDetail(String empNo, EmpUpdateRequestDto updateRequest) {
        empRepository.updateEmp(empNo, updateRequest);
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

    public MailDto sendResetPassword(String empNo, String email) {

        // 사원번호와 이메일 확인
        Employee employee = empRepository.findByEmpNoAndEmail(empNo, email)
                .orElseThrow(() -> new GlobalException(ErrorCode.EMPLOYEE_NOT_FOUND));

        // 임시 비밀번호 생성
        String tempPassword = generateTempPassword();
        if (tempPassword == null || tempPassword.isEmpty()) {
            throw new GlobalException(ErrorCode.TEMP_PASSWORD_GENERATION_FAILED);
        }
        // 비밀번호 업데이트
        String encryptedPassword = passwordEncoder.encode(tempPassword);
        employee.updatePassword(encryptedPassword); // 암호화는 엔티티의 updatePassword 내부에서 처리
        empRepository.save(employee);

        // 메일 데이터 생성
        return new MailDto(
                email,
                employee.getName() + "님의 임시 비밀번호 안내 메일입니다.",
                "안녕하세요.\n\n임시 비밀번호 안내 메일입니다.\n\n" +
                        "임시 비밀번호는 다음과 같습니다:\n" +
                        tempPassword +
                        "\n\n로그인 후 반드시 비밀번호를 변경해주세요."
        );
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return email.matches(emailRegex);
    }

    // 메일 전송
    public SuccessResponse<Boolean> sendMail(MailDto dto) {
        // 이메일 유효성 검증
        String cleanedEmail = dto.getAddress().trim();
        if (!isValidEmail(cleanedEmail)) {
            throw new GlobalException(ErrorCode.INVALID_EMAIL_FORMAT);
        }

        // 메일 전송
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(cleanedEmail);
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
}
