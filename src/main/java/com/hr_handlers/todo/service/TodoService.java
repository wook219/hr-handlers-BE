package com.hr_handlers.todo.service;

import com.hr_handlers.employee.entity.Employee;
import com.hr_handlers.employee.repository.EmpRepository;
import com.hr_handlers.global.dto.SuccessResponse;
import com.hr_handlers.global.exception.ErrorCode;
import com.hr_handlers.global.exception.GlobalException;
import com.hr_handlers.todo.dto.AllTodoResponse;
import com.hr_handlers.todo.dto.TodoModifyRequest;
import com.hr_handlers.todo.dto.TodoRequest;
import com.hr_handlers.todo.dto.TodoResponse;
import com.hr_handlers.todo.entity.Todo;
import com.hr_handlers.todo.mapper.TodoMapper;
import com.hr_handlers.todo.repository.TodoRepository;
import com.hr_handlers.vacation.dto.VacationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;
    private final TodoMapper todoMapper;

    private final EmpRepository empRepository;

    // 모든 일정 조회
    public SuccessResponse<List<AllTodoResponse>> getAllTodo(Long employeeId, String start, String end){

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        Timestamp startDateTime = Timestamp.valueOf(LocalDate.parse(start, formatter).atStartOfDay());
        Timestamp endDateTime = Timestamp.valueOf(LocalDate.parse(end, formatter).atTime(23, 59, 59));

        return SuccessResponse.of(
                "전체 일정 조회 성공",
                todoRepository.findAllTodoByEmployeeId(employeeId, startDateTime, endDateTime)
        );
    }

    // 일정 상세 조회
    public SuccessResponse<TodoResponse> getTodo(Long id){

        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new GlobalException(ErrorCode.TODO_NOT_FOUND));

        return SuccessResponse.of(
                "일정 상세 조회 성공",
                todoMapper.toTodoResponse(todo)
        );
    }

    // 일정 등록
    public SuccessResponse<TodoResponse> enrollTodo(TodoRequest request){
        Employee employee = empRepository.findById(request.getEmployeeId())
                .orElseThrow(() -> new GlobalException(ErrorCode.EMPLOYEE_NOT_FOUND));

        Todo todo = Todo.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .employee(employee)
                .build();

        todoRepository.save(todo);

        return SuccessResponse.of(
                "일정 등록 성공",
                todoMapper.toTodoResponse(todo)
        );
    }

    // 일정 수정
    @Transactional
    public SuccessResponse<TodoResponse> modifyTodo(Long id, TodoModifyRequest request){

        return SuccessResponse.of(
                "일정 수정 성공",
                todoRepository.modifyTodo(id, request)
        );
    }

    // 일정 삭제
    @Transactional
    public SuccessResponse<Boolean> deleteTodo(Long id){

        todoRepository.deleteTodo(id);

        return SuccessResponse.of(
                "일정 삭제 성공",
                Boolean.TRUE
        );
    }
}
