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

        // 사원 목록 검색 쿼리
        List<Employee> results = queryFactory
                .selectFrom(employee)
                .where(condition)
                .offset(pageable.getOffset()) // 페이징 시작 위치
                .limit(pageable.getPageSize()) // 페이징 개수 제한
                .orderBy(employee.createdAt.desc()) // 내림차순 정렬
                .fetch();

        // 페이징을 위한 개수 조회 -> 쿼리 생성
        long total = queryFactory
                .select(employee.count())
                .from(employee)
                .where(condition)
                .fetchOne();

        return new PageImpl<>(results, pageable, total);
    }
}