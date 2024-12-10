package com.hr_handlers.salary.controller;


import com.hr_handlers.global.dto.SuccessResponse;
import com.hr_handlers.salary.dto.response.SalaryResponse;
import com.hr_handlers.salary.service.SalaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.OutputStream;

@RestController
@RequestMapping("/salary")
@RequiredArgsConstructor
public class SalaryController {

    private final SalaryService salaryService;

    // 로그인한 유저의 급여 전체조회
    @GetMapping()
    public SuccessResponse<Page<SalaryResponse>> getUserSalary(
            @PageableDefault(page = 0, size = 15)
            @SortDefault.SortDefaults({
                    @SortDefault(sort = "salary.payDate", direction = Sort.Direction.ASC),
                    @SortDefault(sort = "employee.position", direction = Sort.Direction.ASC)
            }) Pageable pageable,
            Authentication authentication
    )
    {
        return salaryService.getSalaryByUser(pageable, authentication.getName());
    }

    // 급여관리 다운로드
    @PostMapping("/excel/download")
    public SuccessResponse<Boolean> excelDownload(OutputStream stream, Authentication authentication) throws IOException, IllegalAccessException {
        return salaryService.excelDownloadSalary(stream, authentication.getName());
    }
}
