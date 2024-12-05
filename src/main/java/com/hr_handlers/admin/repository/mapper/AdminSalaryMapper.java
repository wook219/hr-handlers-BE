package com.hr_handlers.admin.repository.mapper;

import com.hr_handlers.admin.dto.salary.request.AdminSalaryExcelDownloadRequestDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AdminSalaryMapper {
    List<String> findAll();

    List<AdminSalaryExcelDownloadRequestDto> findAllSalaries();
}
