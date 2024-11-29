package com.hr_handlers.admin.controller;


import com.hr_handlers.admin.dto.salary.request.AdminSalaryCreateRequestDto;
import com.hr_handlers.admin.dto.salary.request.AdminSalaryExcelUploadRequestDto;
import com.hr_handlers.admin.dto.salary.request.AdminSalarySearchRequestDto;
import com.hr_handlers.admin.dto.salary.request.AdminSalaryUpdateRequestDto;
import com.hr_handlers.admin.dto.salary.response.AdminSalaryResponseDto;
import com.hr_handlers.admin.service.AdminSalaryService;
import com.hr_handlers.global.dto.SuccessResponse;
import com.hr_handlers.global.utils.ExcelUploadUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
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

    // 급여 조건 조회
    @PostMapping("search")
    public SuccessResponse<Page<AdminSalaryResponseDto>> searchSalary(
            @PageableDefault(page = 0, size = 10)
            @SortDefault.SortDefaults({
                    @SortDefault(sort = "salary.payDate", direction = Sort.Direction.ASC),
                    @SortDefault(sort = "employee.position", direction = Sort.Direction.ASC)
            }) Pageable pageable,
            @RequestBody @Validated AdminSalarySearchRequestDto adminSalarySearchRequestDto
    ) {
        return adminSalaryService.searchSalary(pageable, adminSalarySearchRequestDto);
    }

    // 급여관리 추가
    @PostMapping()
    public SuccessResponse<Boolean> createSalary(@RequestBody @Validated AdminSalaryCreateRequestDto salaryCreateRequest) {
        return adminSalaryService.createSalary(salaryCreateRequest);
    }

    // 급여관리 수정
    @PutMapping()
    public SuccessResponse<Boolean> updateSalary(@RequestBody @Validated AdminSalaryUpdateRequestDto adminSalaryUpdateRequestDto) {
        return adminSalaryService.updateSalary(adminSalaryUpdateRequestDto);
    }

    // 급여관리 삭제
    @DeleteMapping()
    public SuccessResponse<Boolean> deleteSalary(@RequestBody List<Long> salaryIds) {
        return adminSalaryService.deleteSalary(salaryIds);
    }

    // 급여관리 엑셀 업로드
    @PostMapping("/excel/upload")
    public SuccessResponse<Boolean> excelUpload(@RequestPart(value = "file", required = true) MultipartFile file) throws IOException {
        return adminSalaryService.excelUploadSalary(excelUploadUtils.parseExcelToObject(file, AdminSalaryExcelUploadRequestDto.class));
    }
}
