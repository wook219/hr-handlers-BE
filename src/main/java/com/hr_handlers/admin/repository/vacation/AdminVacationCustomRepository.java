package com.hr_handlers.admin.repository.vacation;

import com.hr_handlers.admin.dto.vacation.AdminVacationResponseDto;
import com.hr_handlers.admin.dto.vacation.AdminVacationStatusResponseDto;

import java.util.List;

public interface AdminVacationCustomRepository {

    List<AdminVacationResponseDto> findPendingVacations();

    List<AdminVacationStatusResponseDto> findVacationStatusForAllEmployees();
}
