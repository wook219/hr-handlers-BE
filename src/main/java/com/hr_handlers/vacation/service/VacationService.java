package com.hr_handlers.vacation.service;

import com.hr_handlers.employee.entity.Employee;
import com.hr_handlers.employee.repository.EmpRepository;
import com.hr_handlers.global.dto.SuccessResponse;
import com.hr_handlers.global.exception.ErrorCode;
import com.hr_handlers.global.exception.GlobalException;
import com.hr_handlers.vacation.dto.*;
import com.hr_handlers.vacation.entity.Vacation;
import com.hr_handlers.vacation.entity.VacationStatus;
import com.hr_handlers.vacation.mapper.VacationMapper;
import com.hr_handlers.vacation.repository.VacationCustomRepository;
import com.hr_handlers.vacation.repository.VacationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VacationService {

    private final VacationRepository vacationRepository;
    private final VacationMapper vacationMapper;

    private final EmpRepository empRepository;

    // 휴가 승인 대기 목록 조회
    public SuccessResponse<List<PendingVacationResponse>> getPendingVacations(Long employeeId){
        List<Vacation> vacations = vacationRepository.findPendingVacations(employeeId);

        List<PendingVacationResponse> response = new ArrayList<>();
        for (Vacation vacation : vacations) {
            response.add(vacationMapper.toPendingVacationResponse(vacation));
        }

        return SuccessResponse.of(
                "승인 대기 휴가 목록 조회 성공",
                response);
    }

    // 휴가 승인 확정 목록 조회
    public SuccessResponse<List<ApprovedVacationResponse>> getApprovedVacations(Long employeeId){
        List<Vacation> vacations = vacationRepository.findApprovedVacations(employeeId);

        List<ApprovedVacationResponse> response = new ArrayList<>();
        for (Vacation vacation : vacations) {
            response.add(vacationMapper.toApprovedVacationResponse(vacation));
        }

        return SuccessResponse.of(
                "승인 확정 휴가 목록 조회 성공",
                response);
    }

    // 휴가 등록
    public SuccessResponse<VacationResponse> enrollVacation(VacationRequest request){
        Employee employee = empRepository.findById(request.getEmployeeId())
                .orElseThrow(() -> new GlobalException(ErrorCode.EMPLOYEE_NOT_FOUND));

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

        VacationResponse response = vacationMapper.toVacationResponse(vacation);

        return SuccessResponse.of(
                "휴가 등록 성공",
                response);
    }

    // 문서 번호 생성 로직
    private String generateDocNum() {
        String year = String.valueOf(LocalDate.now().getYear()); // 현재 연도 가져오기
        String randomPart = UUID.randomUUID().toString().replace("-", "").substring(0, 8); // 랜덤 문자열 8자리
        return year + randomPart;
    }

    public SuccessResponse<VacationResponse> modifyVacation(Long id, VacationModifyRequest request){
        Vacation vacation = vacationRepository.findById(id)
                .orElseThrow(() -> new GlobalException(ErrorCode.VACATION_NOT_FOUND));

        vacation.modify(request);

        vacationRepository.save(vacation);

        VacationResponse response = vacationMapper.toVacationResponse(vacation);

        return SuccessResponse.of(
                "휴가 수정 성공",
                response);
    }

    public SuccessResponse<VacationResponse> deleteVacation(Long id){
        Vacation vacation = vacationRepository.findById(id)
                .orElseThrow(() -> new GlobalException(ErrorCode.VACATION_NOT_FOUND));

        vacationRepository.delete(vacation);

        VacationResponse response = vacationMapper.toVacationResponse(vacation);

        return SuccessResponse.of(
                "휴가 삭제 성공",
                response);
    }
}
