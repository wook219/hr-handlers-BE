package com.hr_handlers.employee.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class TeamDetailResponseDto {
    private String profileImageUrl;
    private String position;
    private String name;
}