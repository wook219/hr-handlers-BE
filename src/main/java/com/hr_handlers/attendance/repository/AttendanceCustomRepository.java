package com.hr_handlers.attendance.repository;

import com.hr_handlers.attendance.dto.EmployeeAttendanceListResponse;

import java.util.List;

public interface AttendanceCustomRepository {

    // 전체 출퇴근 기록 조회
    List<EmployeeAttendanceListResponse> findAllAttendance();
}
