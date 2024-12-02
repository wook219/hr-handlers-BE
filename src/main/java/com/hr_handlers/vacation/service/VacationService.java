package com.hr_handlers.vacation.service;

import com.hr_handlers.employee.entity.Employee;
import com.hr_handlers.employee.repository.EmpRepository;
import com.hr_handlers.global.dto.SuccessResponse;
import com.hr_handlers.global.exception.ErrorCode;
import com.hr_handlers.global.exception.GlobalException;
import com.hr_handlers.vacation.dto.*;
import com.hr_handlers.vacation.entity.Vacation;
import com.hr_handlers.vacation.entity.VacationStatus;
import com.hr_handlers.vacation.entity.VacationType;
import com.hr_handlers.vacation.mapper.VacationMapper;
import com.hr_handlers.vacation.repository.VacationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class VacationService {

    private final VacationRepository vacationRepository;
    private final VacationMapper vacationMapper;

    private final EmpRepository empRepository;

    //휴가 상세 조회
    public SuccessResponse<VacationDetailResponseDto> getVacationDetail(Long id){
        VacationDetailResponseDto response = vacationRepository.findVacationDetailById(id);

        return SuccessResponse.of(
                "휴가 상세 조회 성공",
                response);
    }

    // 휴가 승인 대기 목록 조회
    public SuccessResponse<List<PendingVacationResponseDto>> getPendingVacations(String empNo){
        List<PendingVacationResponseDto> response = vacationRepository.findPendingVacations(empNo);

        return SuccessResponse.of(
                "승인 대기 휴가 목록 조회 성공",
                response);
    }

    // 휴가 승인 확정 목록 조회
    public SuccessResponse<List<ApprovedVacationResponseDto>> getApprovedVacations(String empNo){
        List<ApprovedVacationResponseDto> response = vacationRepository.findApprovedVacations(empNo);

        return SuccessResponse.of(
                "승인 확정 휴가 목록 조회 성공",
                response);
    }

    // 휴가 등록
    public SuccessResponse<VacationResponseDto> enrollVacation(VacationRequestDto request){
        Employee employee = empRepository.findByEmpNo(request.getEmpNo())
                .orElseThrow(() -> new GlobalException(ErrorCode.EMPLOYEE_NOT_FOUND));

        // 휴가 일수 계산
        double deductionDays = calculateVacationDays(
                request.getType(),
                request.getStartDate(),
                request.getEndDate()
        );

        // 잔여 휴가 일수 체크
        checkBalance(request, employee, deductionDays);

        // 휴가 차감
        employee.leaveBalanceUpdate(deductionDays);

        Vacation vacation = Vacation.builder()
                .docNum(generateDocNum())
                .title(request.getTitle())
                .type(request.getType())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .reason(request.getReason())
                .status(VacationStatus.PENDING)
                .employee(employee)
                .build();

        vacationRepository.save(vacation);

        VacationResponseDto response = vacationMapper.toVacationResponse(vacation);

        return SuccessResponse.of(
                "휴가 등록 성공",
                response);
    }

    // 잔여 휴가 일수 체크 로직(공가는 체크하지 않음)
    private void checkBalance(VacationRequestDto request,
                              Employee employee,
                              double deductionDays)
    {
        if (request.getType() != VacationType.PUBLIC) {
            if (employee.getLeaveBalance() < deductionDays) {
                throw new GlobalException(ErrorCode.INSUFFICIENT_LEAVE_BALANCE);
            }
        }
    }

    // 문서 번호 생성 로직
    private String generateDocNum() {
        String year = String.valueOf(LocalDate.now().getYear()); // 현재 연도 가져오기
        String randomPart = UUID.randomUUID().toString().replace("-", "").substring(0, 8); // 랜덤 문자열 8자리
        return year + randomPart;
    }

    // 휴가 일수 계산 로직
    private double calculateVacationDays(VacationType type,
                                         Timestamp startDate,
                                         Timestamp endDate)
    {
        // 고정 일수가 있는 타입(반차, 공가)인 경우
        if (type.getFixedDays() != null) {
            return type.getFixedDays();
        }

        // 연차, 병가의 경우 시작일과 종료일의 차이 계산
        long diff = endDate.getTime() - startDate.getTime();
        // 시작일도 포함해야 하므로 +1
        return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS) + 1;
    }

    // 휴가 수정
    @Transactional
    public SuccessResponse<VacationResponseDto> modifyVacation(Long id, VacationModifyRequestDto request){
        Vacation vacation = vacationRepository.findById(id)
                .orElseThrow(() -> new GlobalException(ErrorCode.VACATION_NOT_FOUND));

        vacation.modify(request);

        vacationRepository.save(vacation);

        VacationResponseDto response = vacationMapper.toVacationResponse(vacation);

        return SuccessResponse.of(
                "휴가 수정 성공",
                response);
    }

    // 휴가 삭제
    public SuccessResponse<VacationResponseDto> deleteVacation(Long id){
        Vacation vacation = vacationRepository.findById(id)
                .orElseThrow(() -> new GlobalException(ErrorCode.VACATION_NOT_FOUND));

        vacationRepository.delete(vacation);

        VacationResponseDto response = vacationMapper.toVacationResponse(vacation);

        return SuccessResponse.of(
                "휴가 삭제 성공",
                response);
    }

    // 휴가 일수 정보 조회
    public SuccessResponse<VacationSummaryResponseDto> getBalance(String empNo){
        VacationSummaryResponseDto response = vacationRepository.findEmployeeVacationBalanceById(empNo);

        System.out.println("response = " + response);

        return  SuccessResponse.of(
                "휴가 일수 정보 조회 성공",
                response);
    }
}
