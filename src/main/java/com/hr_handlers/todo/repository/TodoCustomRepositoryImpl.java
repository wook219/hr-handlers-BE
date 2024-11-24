package com.hr_handlers.todo.repository;

import com.hr_handlers.todo.dto.AllTodoResponse;
import com.hr_handlers.todo.dto.TodoResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.hr_handlers.todo.entity.QTodo.todo;

@Repository
public class TodoCustomRepositoryImpl implements TodoCustomRepository{
    private final JPAQueryFactory jpaQueryFactory;

    public TodoCustomRepositoryImpl(JPAQueryFactory jpaQueryFactory){
        this.jpaQueryFactory = jpaQueryFactory;
    }


    @Override
    public List<AllTodoResponse> findAllTodoByEmployeeId(Long employeeId) {
        return jpaQueryFactory
                .select(
                        Projections.constructor(
                                AllTodoResponse.class,
                                todo.id,
                                todo.title,
                                todo.startTime,
                                todo.endTime,
                                todo.employee.id
                        )
                )
                .from(todo)
                .where(
                        todo.employee.id.eq(employeeId)
                )
                .fetch();
    }
}
