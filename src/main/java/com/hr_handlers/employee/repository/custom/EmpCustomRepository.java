package com.hr_handlers.employee.repository.custom;

import com.hr_handlers.employee.dto.request.EmpUpdateRequestDto;
import com.hr_handlers.employee.dto.response.TeamDetailResponseDto;

import java.util.List;

public interface EmpCustomRepository {

    void updateEmp(String emp, EmpUpdateRequestDto requestDto, String newProfileImageUrl);

    List<TeamDetailResponseDto> findTeamMembers(String empNo);
}