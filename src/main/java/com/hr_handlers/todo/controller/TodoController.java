package com.hr_handlers.todo.controller;

import com.hr_handlers.global.dto.SuccessResponse;
import com.hr_handlers.todo.dto.AllTodoResponseDto;
import com.hr_handlers.todo.dto.TodoModifyRequestDto;
import com.hr_handlers.todo.dto.TodoRequestDto;
import com.hr_handlers.todo.dto.TodoResponseDto;
import com.hr_handlers.todo.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/todo")
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;

    // 모든 일정 조회
    @GetMapping("/{employeeId}")
    public SuccessResponse<List<AllTodoResponseDto>> getAllTodo(
            @PathVariable("employeeId") Long employeeId,
            @RequestParam("start") String start,
            @RequestParam("end") String end)
    {
        return todoService.getAllTodo(employeeId, start, end);
    }

    // 일정 상세 조회
    @GetMapping("/detail/{todoId}")
    public SuccessResponse<TodoResponseDto> getTodo(@PathVariable("todoId") Long id){
        return todoService.getTodo(id);
    }

    @PostMapping
    public SuccessResponse<TodoResponseDto> enrollTodo(@RequestBody TodoRequestDto request){
        return todoService.enrollTodo(request);
    }

    @PutMapping("/{todoId}")
    public SuccessResponse<TodoResponseDto> modifyTodo(@PathVariable("todoId") Long id,
                                                       @RequestBody TodoModifyRequestDto request){
        return todoService.modifyTodo(id, request);
    }

    @DeleteMapping("/{todoId}")
    public SuccessResponse<Boolean> deleteTodo(@PathVariable("todoId") Long id){
        return todoService.deleteTodo(id);
    }
}
