package com.hr_handlers.todo.repository;

import com.hr_handlers.global.exception.ErrorCode;
import com.hr_handlers.global.exception.GlobalException;
import com.hr_handlers.todo.dto.AllTodoResponse;
import com.hr_handlers.todo.dto.TodoModifyRequest;
import com.hr_handlers.todo.dto.TodoResponse;
import com.hr_handlers.todo.entity.Todo;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import static com.hr_handlers.todo.entity.QTodo.todo;

@Repository
@RequiredArgsConstructor
public class TodoCustomRepositoryImpl implements TodoCustomRepository{

    private final JPAQueryFactory jpaQueryFactory;
    private final EntityManager entityManager;

    @Override
    public List<AllTodoResponse> findAllTodoByEmployeeId(
            Long employeeId,
            Timestamp start,
            Timestamp end)
    {
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
                        todo.employee.id.eq(employeeId),
                        todo.startTime.goe(start),
                        todo.endTime.loe(end)
                )
                .fetch();
    }

    @Override
    public void deleteTodo(Long id) {
        long deletedCount = jpaQueryFactory
                .delete(todo)
                .where(todo.id.eq(id))
                .execute();

        if(deletedCount == 0){
            throw new GlobalException(ErrorCode.TODO_NOT_FOUND);
        }
    }

    @Override
    public TodoResponse modifyTodo(Long id, TodoModifyRequest request) {

        long updatedCount = jpaQueryFactory
                .update(todo)
                .where(todo.id.eq(id))
                .set(todo.title, request.getTitle())
                .set(todo.content, request.getContent())
                .set(todo.startTime, request.getStartTime())
                .set(todo.endTime, request.getEndTime())
                .execute();

        entityManager.flush();
        entityManager.clear();

        if(updatedCount == 0){
            throw new GlobalException(ErrorCode.TODO_NOT_FOUND);
        }

        return jpaQueryFactory
                .select(
                        Projections.constructor(
                                TodoResponse.class,
                                todo.id,
                                todo.title,
                                todo.content,
                                todo.startTime,
                                todo.endTime,
                                todo.employee.id
                        )
                )
                .from(todo)
                .where(todo.id.eq(id))
                .fetchOne();
    }
}
