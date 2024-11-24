package com.hr_handlers.attendance.mapper;

import com.hr_handlers.attendance.dto.CheckInResponse;
import com.hr_handlers.attendance.dto.CheckOutResponse;
import com.hr_handlers.attendance.entity.Attendance;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", uses = {}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AttendanceMapper {

    @Mapping(source = "employee.id", target = "employeeId")
    CheckInResponse toCheckInResponse(Attendance attendance);

    @Mapping(source = "employee.id", target = "employeeId")
    CheckOutResponse toCheckOutResponse(Attendance attendance);
}
