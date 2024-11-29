package com.hr_handlers.todo.controller;

import com.hr_handlers.global.dto.SuccessResponse;
import com.hr_handlers.todo.dto.*;
import com.hr_handlers.todo.service.HolidayService;
import com.hr_handlers.todo.service.TodoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/todo")
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;
    private final HolidayService holidayService;

    // 월별 일정 조회
    @GetMapping("/{empNo}")
    public SuccessResponse<List<AllTodoResponseDto>> getAllTodo(
            @PathVariable("empNo") String empNo,
            @RequestParam("start") String start,
            @RequestParam("end") String end)
    {
        return todoService.getAllTodo(empNo, start, end);
    }

    // 월별 공휴일 조회
    @GetMapping("/holidays/{year}/{month}")
    public SuccessResponse<List<HolidayResponseDto>> getHolidays(
            @PathVariable("year") int year,
            @PathVariable("month") int month
    ){
        return holidayService.getHolidays(year, month);
    }

    // 일정 상세 조회
    @GetMapping("/detail/{todoId}")
    public SuccessResponse<TodoResponseDto> getTodo(@PathVariable("todoId") Long id){
        return todoService.getTodo(id);
    }

    @PostMapping
    public SuccessResponse<TodoResponseDto> enrollTodo(@Valid @RequestBody TodoRequestDto request){
        return todoService.enrollTodo(request);
    }

    @PutMapping("/{todoId}")
    public SuccessResponse<TodoResponseDto> modifyTodo(@PathVariable("todoId") Long id,
                                                       @Valid @RequestBody TodoModifyRequestDto request){
        return todoService.modifyTodo(id, request);
    }

    @DeleteMapping("/{todoId}")
    public SuccessResponse<Boolean> deleteTodo(@PathVariable("todoId") Long id){
        return todoService.deleteTodo(id);
    }
}
