package com.hr_handlers.vacation.mapper;

import com.hr_handlers.vacation.dto.ApprovedVacationResponse;
import com.hr_handlers.vacation.dto.PendingVacationResponse;
import com.hr_handlers.vacation.dto.VacationResponse;
import com.hr_handlers.vacation.entity.Vacation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", uses = {}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface VacationMapper {

    @Mapping(source = "employee.id", target = "employeeId")
    PendingVacationResponse toPendingVacationResponse(Vacation vacation);

    @Mapping(source = "employee.id", target = "employeeId")
    @Mapping(source = "approvedAt", target = "approvedAt")
    @Mapping(source = "approver", target = "approver")
    ApprovedVacationResponse toApprovedVacationResponse(Vacation vacation);

    @Mapping(source = "employee.id", target = "employeeId")
    VacationResponse toVacationResponse(Vacation vacation);
}
