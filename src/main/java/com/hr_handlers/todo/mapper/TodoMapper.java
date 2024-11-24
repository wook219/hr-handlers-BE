package com.hr_handlers.todo.mapper;

import com.hr_handlers.todo.dto.TodoResponse;
import com.hr_handlers.todo.entity.Todo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", uses = {}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TodoMapper {

    @Mapping(source = "employee.id", target = "employeeId")
    TodoResponse toTodoResponse(Todo todo);
}
