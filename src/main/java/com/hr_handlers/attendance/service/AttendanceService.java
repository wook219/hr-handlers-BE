package com.hr_handlers.attendance.service;

import com.hr_handlers.attendance.dto.*;
import com.hr_handlers.attendance.entity.Attendance;
import com.hr_handlers.attendance.entity.AttendanceStatus;
import com.hr_handlers.attendance.mapper.AttendanceMapper;
import com.hr_handlers.attendance.repository.AttendanceRepository;
import com.hr_handlers.employee.entity.Employee;
import com.hr_handlers.employee.repository.EmpRepository;
import com.hr_handlers.global.dto.SuccessResponse;
import com.hr_handlers.global.exception.ErrorCode;
import com.hr_handlers.global.exception.GlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final AttendanceMapper attendanceMapper;

    private final EmpRepository empRepository;

    Timestamp currentTime = Timestamp.valueOf(LocalDateTime.now());

    public SuccessResponse<List<EmployeeAttendanceListResponseDto>> getAllAttendance(){
        List<EmployeeAttendanceListResponseDto> response = attendanceRepository.findAllAttendance();

        return SuccessResponse.of(
                "전직원 출퇴근 조회 성공",
                response
        );
    }

    public SuccessResponse<CheckInResponseDto> checkIn(CheckInRequestDto request){
        Employee employee = empRepository.findById(request.getEmployeeId())
                .orElseThrow(() -> new GlobalException(ErrorCode.EMPLOYEE_NOT_FOUND));

        Attendance attendance = Attendance.builder()
                .status(AttendanceStatus.WORK)
                .checkInTime(currentTime)
                .employee(employee)
                .build();

        attendanceRepository.save(attendance);

        CheckInResponseDto response = attendanceMapper.toCheckInResponse(attendance);

        return SuccessResponse.of(
                "출근 성공",
                response
        );
    }

    public SuccessResponse<CheckOutResponseDto> checkOut(Long id){
        Attendance attendance = attendanceRepository.findById(id)
                .orElseThrow(() -> new GlobalException(ErrorCode.ATTENDANCE_NOT_FOUND));

        attendance.modify();

        attendanceRepository.save(attendance);

        CheckOutResponseDto response = attendanceMapper.toCheckOutResponse(attendance);

        return SuccessResponse.of(
                "퇴근 성공",
                response
        );
    }
}
