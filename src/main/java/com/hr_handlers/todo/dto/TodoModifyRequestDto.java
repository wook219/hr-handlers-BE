package com.hr_handlers.todo.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TodoModifyRequestDto {

    @NotEmpty(message = "제목을 입력해주세요.")
    @Size(min = 1, max = 20, message = "20자 이하로 입력해주세요.")
    private String title;

    @Size(max = 200, message = "200자 이하로 입력해주세요.")
    private String content;

    @NotEmpty(message = "시작 시간를 선택해주세요.")
    private Timestamp startTime;

    @NotEmpty(message = "종료 시간를 선택해주세요.")
    private Timestamp endTime;
}
