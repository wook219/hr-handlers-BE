package com.hr_handlers.admin.controller;


import com.hr_handlers.admin.dto.salary.request.AdminSalaryCreateRequestDto;
import com.hr_handlers.admin.dto.salary.request.AdminSalaryExcelUploadRequestDto;
import com.hr_handlers.admin.dto.salary.request.AdminSalaryUpdateRequestDto;
import com.hr_handlers.admin.dto.salary.response.AdminSalaryResponseDto;
import com.hr_handlers.admin.service.AdminSalaryService;
import com.hr_handlers.global.dto.SuccessResponse;
import com.hr_handlers.global.utils.ExcelUploadUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/admin/salary")
@RequiredArgsConstructor
public class AdminSalaryController {

    private final AdminSalaryService adminSalaryService;
    private final ExcelUploadUtils excelUploadUtils;

    // 모든 유저의 급여 전체 조회
    @GetMapping()
    public SuccessResponse<List<AdminSalaryResponseDto>> getAllUserSalary() {
        return adminSalaryService.getAllUserSalary();
    }

    @PostMapping()
    public SuccessResponse<Boolean> createSalary(@RequestBody @Validated AdminSalaryCreateRequestDto salaryCreateRequest) {
        return adminSalaryService.createSalary(salaryCreateRequest);
    }

    @PutMapping()
    public SuccessResponse<Boolean> updateSalary(@RequestBody @Validated AdminSalaryUpdateRequestDto adminSalaryUpdateRequestDto) {
        return adminSalaryService.updateSalary(adminSalaryUpdateRequestDto);
    }

    @DeleteMapping()
    public SuccessResponse<Boolean> deleteSalary(@RequestBody List<Long> salaryIds) {
        return adminSalaryService.deleteSalary(salaryIds);
    }

    @PostMapping("/excel/upload")
    public SuccessResponse<Boolean> excelUpload(@RequestPart(value = "file", required = true) MultipartFile file) throws IOException {
        List<AdminSalaryExcelUploadRequestDto> adminSalaryExcelUploadRequestDtos = excelUploadUtils.parseExcelToObject(file, AdminSalaryExcelUploadRequestDto.class);
        return adminSalaryService.excelUploadSalary(adminSalaryExcelUploadRequestDtos);
    }
}
