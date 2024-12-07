package com.hr_handlers.attendance.repository;

import com.hr_handlers.attendance.dto.AttendanceHistoryResponseDto;
import com.hr_handlers.attendance.dto.AttendanceHistorySearchDto;
import com.hr_handlers.attendance.dto.EmployeeAttendanceListResponseDto;
import com.hr_handlers.attendance.dto.EmployeeAttendanceResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AttendanceCustomRepository {

    // 전체 출퇴근 기록 조회
    List<EmployeeAttendanceListResponseDto> findAllAttendance();

    EmployeeAttendanceResponseDto findAttendance(String empNo);

    Page<AttendanceHistoryResponseDto> findAttendanceHistory(
            String empNo,
            AttendanceHistorySearchDto searchDto,
            Pageable pageable
    );
}
