package com.hr_handlers.todo.controller;

import com.hr_handlers.global.dto.SuccessResponse;
import com.hr_handlers.todo.dto.AllTodoResponse;
import com.hr_handlers.todo.dto.TodoModifyRequest;
import com.hr_handlers.todo.dto.TodoRequest;
import com.hr_handlers.todo.dto.TodoResponse;
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
    public SuccessResponse<List<AllTodoResponse>> getAllTodo(@PathVariable("employeeId") Long employeeId){
        return todoService.getAllTodo(employeeId);
    }

    // 일정 상세 조회
    @GetMapping("/detail/{todoId}")
    public SuccessResponse<TodoResponse> getTodo(@PathVariable("todoId") Long id){
        return todoService.getTodo(id);
    }

    @PostMapping
    public SuccessResponse<TodoResponse> enrollTodo(@RequestBody TodoRequest request){
        return todoService.enrollTodo(request);
    }

    @PutMapping("/{todoId}")
    public SuccessResponse<TodoResponse> modifyTodo(@PathVariable("todoId") Long id,
                                                    @RequestBody TodoModifyRequest request){
        return todoService.modifyTodo(id, request);
    }

    @DeleteMapping("/{todoId}")
    public SuccessResponse<TodoResponse> deleteTodo(@PathVariable("todoId") Long id){
        return todoService.deleteTodo(id);
    }
}
