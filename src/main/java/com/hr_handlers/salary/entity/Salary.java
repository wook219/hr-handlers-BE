package com.hr_handlers.salary.entity;

import com.hr_handlers.employee.entity.Employee;
import com.hr_handlers.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Salary extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @Column(name = "basic_salary", nullable = false)
    private int basicSalary;

    @Column(name = "deduction", nullable = false)
    private int deduction;

    @Column(name = "net_salary", nullable = false)
    private int netSalary;

    @Column(name = "pay_date", nullable = false, columnDefinition = "DATE")
    private LocalDate payDate;
}
