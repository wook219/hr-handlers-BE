package com.hr_handlers.admin.controller;


import com.hr_handlers.admin.dto.salary.request.AdminSalaryCreateRequest;
import com.hr_handlers.admin.dto.salary.request.AdminSalaryExcelUploadRequest;
import com.hr_handlers.admin.dto.salary.response.AdminSalaryResponse;
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
    public SuccessResponse<List<AdminSalaryResponse>> getAllUserSalary() {
        return adminSalaryService.getAllUserSalary();
    }

    @PostMapping()
    public SuccessResponse createSalary(@RequestBody @Validated AdminSalaryCreateRequest salaryCreateRequest) {
        return adminSalaryService.createSalary(salaryCreateRequest);
    }

    @DeleteMapping()
    public SuccessResponse deleteSalary(@RequestBody List<Long> salaryIds) {
        return adminSalaryService.deleteSalary(salaryIds);
    }

    @PostMapping("/excel/upload")
    public SuccessResponse excelUpload(@RequestPart(value = "file", required = true) MultipartFile file) throws IOException {
        List<AdminSalaryExcelUploadRequest> adminSalaryExcelUploadRequests = excelUploadUtils.parseExcelToObject(file, AdminSalaryExcelUploadRequest.class);
        return adminSalaryService.excelUploadSalary(adminSalaryExcelUploadRequests);
    }
}
