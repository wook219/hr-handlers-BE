package com.hr_handlers.attendance.repository;

import com.hr_handlers.attendance.dto.EmployeeAttendanceListResponseDto;

import java.util.List;

public interface AttendanceCustomRepository {

    // 전체 출퇴근 기록 조회
    List<EmployeeAttendanceListResponseDto> findAllAttendance();
}
