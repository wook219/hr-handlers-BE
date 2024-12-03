package com.hr_handlers.admin.repository.employee;

import com.hr_handlers.admin.dto.employee.request.AdminEmpUpdateRequestDto;
import com.hr_handlers.employee.entity.Department;
import com.hr_handlers.employee.entity.Employee;
import com.hr_handlers.employee.entity.QEmployee;
import com.hr_handlers.employee.enums.ContractType;
import com.hr_handlers.employee.repository.DeptRepository;
import com.hr_handlers.global.exception.ErrorCode;
import com.hr_handlers.global.exception.GlobalException;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.hr_handlers.employee.entity.QEmployee.employee;

@Repository
@RequiredArgsConstructor
public class AdminEmpCustomRepositoryImpl implements AdminEmpCustomRepository {

    private final JPAQueryFactory queryFactory;
    private final EntityManager entityManager;
    private final DeptRepository deptRepository;

    @Override
    public Page<Employee> findEmpByName(String keyword, Pageable pageable) {
        QEmployee employee = QEmployee.employee;

        // 검색 조건 생성
        BooleanExpression condition = (keyword != null && !keyword.trim().isEmpty())
                ? employee.name.containsIgnoreCase(keyword)
                : null; // 전체 조회

        // 사원 목록 검색 쿼리
        List<Employee> results = queryFactory
                .selectFrom(employee)
                .where(condition) // 검색 없으면 전체 조회, 있으면 필터링
                .offset(pageable.getOffset()) // 페이징 시작 위치
                .limit(pageable.getPageSize()) // 페이징 개수 제한
                .orderBy(employee.createdAt.desc()) // 내림차순 정렬
                .fetch();

        Long total = queryFactory
                .select(employee.count())
                .from(employee)
                .where(condition)
                .fetchOne();

        return new PageImpl<>(results, pageable, total);
    }

    @Override
    @Transactional
    public void updateEmp(String empNo, AdminEmpUpdateRequestDto updateRequest) {

        // TODO: 부서 관련
        // 새로운 Department 객체 생성
        Department newDepartment = Department.builder()
                .deptName(updateRequest.getDeptName())
                .build();

        // DB에 저장 (이미 존재하는 부서는 가져오고, 없으면 새로 생성)
        Department department = deptRepository.findByDeptName(updateRequest.getDeptName())
                .orElseGet(() -> deptRepository.save(newDepartment));

        long updatedCount = queryFactory
                .update(employee)
                .where(employee.empNo.eq(empNo))
                .set(employee.position, updateRequest.getPosition())
                .set(employee.contractType, ContractType.valueOf(updateRequest.getContractType()))
                .set(employee.leaveBalance, updateRequest.getLeaveBalance())
                .set(employee.department, department) // 부서를 새로운 객체로 교체
                .execute();

        entityManager.flush();
        entityManager.clear();

        if (updatedCount == 0) {
            throw new GlobalException(ErrorCode.EMPLOYEE_NOT_FOUND);
        }
    }

    @Override
    @Transactional
    public void deleteEmp(String empNo) {
        long deletedCount = queryFactory
                .delete(employee)
                .where(employee.empNo.eq(empNo))
                .execute();

        if(deletedCount == 0){
            throw new GlobalException(ErrorCode.TODO_NOT_FOUND);
        }
    }
}