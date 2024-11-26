package com.hr_handlers.admin.repository.search;

import com.hr_handlers.employee.entity.Employee;
import com.hr_handlers.employee.entity.QEmployee;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EmpSearchRepositoryImpl implements EmpSearchRepository {

    private final JPAQueryFactory queryFactory;

    public EmpSearchRepositoryImpl(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public Page<Employee> findEmpByName(String keyword, Pageable pageable) {
        QEmployee employee = QEmployee.employee;

        // 검색 조건 생성
        BooleanExpression condition = keyword != null && !keyword.trim().isEmpty()
                ? employee.name.containsIgnoreCase(keyword)
                : null;

        List<Employee> results = queryFactory
                .selectFrom(employee)
                .where(condition)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(employee.createdAt.desc())
                .fetch();

        // 개수 조회
        long total = queryFactory
                .select(employee.count())
                .from(employee)
                .where(condition)
                .fetchOne();

        return new PageImpl<>(results, pageable, total);
    }
}