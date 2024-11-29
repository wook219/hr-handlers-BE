package com.hr_handlers.employee.repository.custom;

import com.hr_handlers.employee.dto.request.EmpUpdateRequestDto;

public interface EmpCustomRepository {

    void updateEmp(String emp, EmpUpdateRequestDto requestDto);
}