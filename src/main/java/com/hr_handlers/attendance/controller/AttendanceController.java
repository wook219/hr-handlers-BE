package com.hr_handlers.attendance.controller;

import com.hr_handlers.attendance.dto.*;
import com.hr_handlers.attendance.service.AttendanceService;
import com.hr_handlers.global.dto.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/attendance")
@RequiredArgsConstructor
public class AttendanceController {

    private final AttendanceService attendanceService;

    @GetMapping
    public SuccessResponse<List<EmployeeAttendanceListResponseDto>> getAllAttendance(){
        return attendanceService.getAllAttendance();
    }

    @PostMapping
    public SuccessResponse<CheckInResponseDto> checkIn(@RequestBody CheckInRequestDto request){
        return attendanceService.checkIn(request);
    }

    @PutMapping("/{attendanceId}")
    public SuccessResponse<CheckOutResponseDto> checkOut(@PathVariable("attendanceId") Long id){
        return attendanceService.checkOut(id);
    }
}
