package com.hr_handlers.salary.service;

import com.hr_handlers.global.dto.SuccessResponse;
import com.hr_handlers.salary.dto.response.SalaryResponse;
import com.hr_handlers.salary.repository.SalaryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SalaryService {

    private final SalaryRepository salaryRepository;

    public SuccessResponse<List<SalaryResponse>> getSalaryByUser() {

        // todo: 로그인 기능 완료시 주석 내용 수정해서 employeeId 가져오기
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        Long employeeId = authentication.getId();
        Long employeeId = 1L;

        List<SalaryResponse> salaryResponse = salaryRepository.findSalaryByEmployeeId(employeeId);
        return SuccessResponse.of(
                "급여 관리 조회 성공",
                salaryResponse);
    }
}
