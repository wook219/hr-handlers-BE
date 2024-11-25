package com.hr_handlers.attendance.repository;

import com.hr_handlers.attendance.dto.EmployeeAttendanceListResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.hr_handlers.attendance.entity.QAttendance.attendance;

@Repository
public class AttendanceCustomRepositoryImpl implements AttendanceCustomRepository{

    private final JPAQueryFactory jpaQueryFactory;

    public AttendanceCustomRepositoryImpl(JPAQueryFactory jpaQueryFactory){
        this.jpaQueryFactory = jpaQueryFactory;
    }


    @Override
    public List<EmployeeAttendanceListResponse> findAllAttendance() {
        return jpaQueryFactory
                .select(
                        Projections.constructor(
                                EmployeeAttendanceListResponse.class,
                                attendance.status,
                                attendance.checkInTime,
                                attendance.checkOutTime,
                                attendance.employee.name
                        )
                )
                .from(attendance)
                .fetch();
    }
}
