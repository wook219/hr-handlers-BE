package com.hr_handlers.attendance.entity;

import com.hr_handlers.employee.entity.Employee;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "attendance")
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private AttendanceStatus status;

    @Column(name = "check_in_time", nullable = false)
    private Timestamp checkInTime;

    @Column(name = "check_out_time")
    private Timestamp checkOutTime;

    @ManyToOne
    @JoinColumn(name = "employee_id", referencedColumnName = "id", nullable = false)
    private Employee employee;

    public void modify(){
        this.checkOutTime = Timestamp.valueOf(LocalDateTime.now());
    }
}
