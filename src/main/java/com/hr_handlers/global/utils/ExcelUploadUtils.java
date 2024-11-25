package com.hr_handlers.global.utils;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Component
public class ExcelUploadUtils implements ExcelUtilMethodFactory {
    public <T> List<T> parseExcelToObject(MultipartFile file, Class<T> clazz) throws IOException {
        Workbook workbook = WorkbookFactory.create(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);

        parseHeader(sheet, clazz);
        return parseBody(sheet, clazz);
    }

    public <T> void parseHeader(Sheet sheet, Class<T> clazz) {
        Set<String> excelHeaders = new HashSet<>();
        Set<String> classHeaders = new HashSet<>();
        int headerStartRowToParse = 0;

        sheet.getRow(headerStartRowToParse).cellIterator()
                .forEachRemaining(e -> excelHeaders.add(e.getStringCellValue()));

        Arrays.stream(clazz.getDeclaredFields())
                .filter(e -> e.isAnnotationPresent(ExcelColumn.class))
                .forEach(e -> {
                    if (e.getAnnotation(ExcelColumn.class).headerName().equals("")) {
                        classHeaders.add(e.getName());
                    }
                    else {
                        classHeaders.add(e.getAnnotation(ExcelColumn.class).headerName());
                    }
                });

        if (!excelHeaders.containsAll(classHeaders)) {
            // 업로드한 엑셀 헤더가 엑셀Dto에 선언한 내용과 불일치 할경우
            //todo exception 따로처리
            throw new IllegalStateException("헤더 불일치.");
        }
    }

    public <T> List<T> parseBody(Sheet sheet, Class<T> clazz) {
        List<T> objects = new ArrayList<>();
        try {
            var constructor = clazz.getDeclaredConstructor();
            constructor.setAccessible(true);
            var fillUpFromRowMethod = clazz.getMethod("fillUpFromRow", Row.class);

            for(int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null || row.getPhysicalNumberOfCells() == 0) continue;

                T object = constructor.newInstance();
                fillUpFromRowMethod.invoke(object, row);
                objects.add(object);
            }
        } catch (NoSuchMethodException e) {
            // todo: exception 따로처리
            throw new IllegalArgumentException("클래스 " + clazz.getName() + "에 fillUpFromRow 메서드가 없습니다.", e);
        } catch (Exception e) {
            // todo: exception 따로처리
            throw new RuntimeException("Excel 데이터 변환 중 오류가 발생했습니다.", e);
        }
        return objects;
    }
}
