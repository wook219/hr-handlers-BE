package com.hr_handlers.attendance.controller;

import com.hr_handlers.attendance.dto.*;
import com.hr_handlers.attendance.service.AttendanceService;
import com.hr_handlers.global.dto.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/attendance")
@RequiredArgsConstructor
public class AttendanceController {

    private final AttendanceService attendanceService;

    // 출 퇴근 조회
    @GetMapping
    public SuccessResponse<EmployeeAttendanceResponseDto> getAttendance(Authentication authentication){
        return attendanceService.getAttendance(authentication.getName());
    }

    @GetMapping("/history")
    public SuccessResponse<Page<AttendanceHistoryResponseDto>> getAttendanceHistory
    (
        Authentication authentication,
        @RequestParam(required = false, value = "checkInTime")
        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")  LocalDateTime checkInTime,
        @RequestParam(required = false, value = "checkOutTime")
        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime checkOutTime,
        @PageableDefault(size = 10, sort = "checkInTime", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        AttendanceHistorySearchDto searchDto = new AttendanceHistorySearchDto(checkInTime, checkOutTime);
        return attendanceService.getAttendanceHistory(
                authentication.getName(),
                searchDto,
                pageable
        );
    }

    // 출근
    @PostMapping
    public SuccessResponse<CheckInResponseDto> checkIn(Authentication authentication){
        return attendanceService.checkIn(authentication.getName());
    }

    // 퇴근
    @PutMapping("/{id}")
    public SuccessResponse<CheckOutResponseDto> checkOut(@PathVariable("id") Long id){
        return attendanceService.checkOut(id);
    }
}
