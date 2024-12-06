package com.hr_handlers.admin.repository.employee;

import com.hr_handlers.employee.entity.Department;
import com.hr_handlers.employee.entity.QDepartment;
import com.hr_handlers.employee.entity.QEmployee;
import com.hr_handlers.global.exception.ErrorCode;
import com.hr_handlers.global.exception.GlobalException;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class AdminDeptCustomRepositoryImpl implements AdminDeptCustomRepository{

    private final JPAQueryFactory queryFactory;
    private final EntityManager entityManager;


    @Override
    @Transactional
    public void updateDept(Long id, String deptName) {
        long updatedCount = queryFactory
                .update(QDepartment.department)
                .where(QDepartment.department.id.eq(id))
                .set(QDepartment.department.deptName, deptName)
                .execute();

        if (updatedCount == 0) {
            throw new GlobalException(ErrorCode.DEPARTMENT_NOT_FOUND);
        }

        entityManager.flush();
        entityManager.clear();
    }

    @Override
    @Transactional
    public void deleteDept(Long id) {
        queryFactory
                .update(QEmployee.employee)
                .set(QEmployee.employee.department, (Department) null)
                .where(QEmployee.employee.department.id.eq(id))
                .execute();

        long deletedCount = queryFactory
                .delete(QDepartment.department)
                .where(QDepartment.department.id.eq(id))
                .execute();

        if (deletedCount == 0) {
            throw new GlobalException(ErrorCode.DEPARTMENT_NOT_FOUND);
        }

        entityManager.flush();
        entityManager.clear();
    }
}
