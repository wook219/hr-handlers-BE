package com.hr_handlers.global.utils;

import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ExcelUtilMethodFactory {
    public <T> List<T> parseExcelToObject(MultipartFile file, Class<T> clazz)  throws IOException;

    public <T> void parseHeader(Sheet sheet, Class<T> clazz);
    public <T> List<T> parseBody(Sheet sheet, Class<T> clazz);
}