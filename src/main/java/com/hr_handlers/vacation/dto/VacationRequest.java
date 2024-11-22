package com.hr_handlers.vacation.dto;

import com.hr_handlers.vacation.entity.VacationType;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class VacationRequest {

    @NotEmpty(message = "제목을 입력해주세요.")
    private String title;

    @NotEmpty(message = "휴가 유형을 선택해주세요.")
    private VacationType type;

    @NotEmpty(message = "시작 일자를 선택해주세요.")
    @Future
    private Timestamp startDate;

    @NotEmpty(message = "종료 일자를 선택해주세요.")
    @Future
    private Timestamp endDate;

    @NotEmpty(message = "휴가 사유를 입력해주세요")
    private String reason;

    private Long employeeId;
}
