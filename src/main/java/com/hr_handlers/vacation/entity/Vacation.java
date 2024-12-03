package com.hr_handlers.vacation.entity;

import com.hr_handlers.employee.entity.Employee;
import com.hr_handlers.global.entity.BaseTimeEntity;
import com.hr_handlers.vacation.dto.VacationRequestDto;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "vacation")
public class Vacation extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "doc_num", nullable = false)
    private String docNum;

    @Column(name = "title", nullable = false)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private VacationType type;

    @Column(name = "start_date", nullable = false)
    private Timestamp startDate;

    @Column(name = "end_date", nullable = false)
    private Timestamp endDate;

    @Column(name = "reason", nullable = false)
    private String reason;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private VacationStatus status;

    @Column(name = "approved_at")
    private Timestamp approvedAt;

    @Column(name = "approver")
    private String approver;

//    @Column(name = "employee_id", nullable = false)
//    private Long employee_id;

    @ManyToOne
    @JoinColumn(name = "employee_id", referencedColumnName = "id", nullable = false)
    private Employee employee;


    public void modify(VacationRequestDto request){
        this.title = request.getTitle();
        this.type = request.getType();
        this.startDate = request.getStartDate();
        this.endDate = request.getEndDate();
        this.reason = request.getReason();
    }
}
