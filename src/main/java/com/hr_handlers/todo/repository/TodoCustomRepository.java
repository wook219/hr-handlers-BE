package com.hr_handlers.todo.repository;

import com.hr_handlers.todo.dto.AllTodoResponse;
import com.hr_handlers.todo.dto.TodoModifyRequest;
import com.hr_handlers.todo.dto.TodoResponse;
import com.hr_handlers.todo.entity.Todo;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

public interface TodoCustomRepository {

    // 일정 전체 조회
    List<AllTodoResponse> findAllTodoByEmployeeId(
            Long employeeId,
            Timestamp start,
            Timestamp end);

    // 일정 삭제
    void deleteTodo(Long id);

    // 일정 수정
    TodoResponse modifyTodo(Long id, TodoModifyRequest request);
}
