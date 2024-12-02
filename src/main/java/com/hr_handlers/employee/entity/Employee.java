package com.hr_handlers.employee.entity;

import com.hr_handlers.employee.enums.ContractType;
import com.hr_handlers.employee.enums.Role;
import com.hr_handlers.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Employee extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long id;

    @Column(name = "emp_no", nullable = false, unique = true)
    @Comment(value = "사원 번호(아이디)")
    private String empNo;

    @Column(name = "position", nullable = false)
    @Comment(value = "직급")
    private String position;

    @Column(name = "name", nullable = false)
    @Comment(value = "사원 이름")
    private String name;

    @Column(name = "email", nullable = false)
    @Comment(value = "이메일")
    private String email;

    @Column(name = "password", nullable = false)
    @Comment(value = "비밀번호")
    private String password;

    @Column(name = "phone", nullable = false, length = 13)
    @Comment(value = "연락처")
    private String phone;

    @Column(name = "birth_date", nullable = false)
    @Comment(value = "생년월일")
    private LocalDate birthDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "contract_type", nullable = true)
    @Comment(value = "계약형태")
    private ContractType contractType;

    @Column(name = "join_date", nullable = false)
    @Comment(value = "입사일")
    private LocalDate joinDate;

    @Column(name = "introduction", nullable = true)
    @Comment(value = "자기소개")
    private String introduction;

    @Column(name = "leave_balance", nullable = false, columnDefinition = "DECIMAL(4,1)")
    @Comment(value = "휴가잔여일수")
    private Double leaveBalance;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    @Comment(value = "권한")
    private Role role;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "profile_image_id")
    private ProfileImage profileImage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private Department department;

    // Update 메서드
    public void empUpdate(String email, String phone, String introduction, String profileImage) {
        this.email = email;
        this.phone = phone;
        this.introduction = introduction;

        if (profileImage != null) {
            this.profileImage = ProfileImage.builder()
                    .profileImageUrl(profileImage)
                    .build();
        }
    }

    public void adminUpdate(String empNo, String contractType, String position, Double leaveBalance, String departmentName) {
        this.empNo = empNo;
        this.contractType = ContractType.valueOf(contractType);
        this.position = position;
        this.leaveBalance = leaveBalance;

        if (departmentName != null && this.department != null) {
            this.department = Department.builder()
                    .deptName(departmentName)
                    .build();
        }
    }

    public void leaveBalanceUpdate(Double deductionDays){
        this.leaveBalance -= deductionDays;
    }
}