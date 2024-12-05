package com.hr_handlers.employee.dto.response;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@Builder
public class MailDto {

    @NotBlank
    private String address;

    @NotBlank
    private String title;

    @NotBlank
    private String message;
}
