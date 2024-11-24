package com.hr_handlers.todo.repository;

import com.hr_handlers.todo.dto.AllTodoResponse;
import com.hr_handlers.todo.dto.TodoResponse;

import java.util.List;

public interface TodoCustomRepository {

    // 일정 전체 조회
    List<AllTodoResponse> findAllTodoByEmployeeId(Long employeeId);
}
