package com.hr_handlers.admin.repository.employee;

public interface AdminDeptCustomRepository {

    void updateDept(Long id, String deptName);

    void deleteDept(Long id);
}
