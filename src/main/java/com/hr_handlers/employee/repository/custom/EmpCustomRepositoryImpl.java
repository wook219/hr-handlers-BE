package com.hr_handlers.employee.repository.custom;

import com.hr_handlers.employee.dto.request.EmpUpdateRequestDto;
import com.hr_handlers.employee.entity.ProfileImage;
import com.hr_handlers.employee.repository.ProfileImageRepository;
import com.hr_handlers.global.exception.ErrorCode;
import com.hr_handlers.global.exception.GlobalException;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import static com.hr_handlers.employee.entity.QEmployee.employee;

@Repository
@RequiredArgsConstructor
public class EmpCustomRepositoryImpl implements EmpCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;
    private final EntityManager entityManager;
    private final ProfileImageRepository profileImageRepository;

    @Override
    @Transactional
    public void updateEmp(String empNo, EmpUpdateRequestDto requestDto) {

        // 프로필 이미지 변경 및 생성
        ProfileImage profileImage = ProfileImage.builder()
                .profileImageUrl(requestDto.getProfileImageUrl())
                .build();
        profileImageRepository.save(profileImage);

        long updatedCount = jpaQueryFactory
                .update(employee)
                .where(employee.empNo.eq(empNo))
                .set(employee.email, requestDto.getEmail())
                .set(employee.phone, requestDto.getPhone())
                .set(employee.introduction, requestDto.getIntroduction())
                .set(employee.profileImage, profileImage)
                .execute();

        entityManager.flush();
        entityManager.close();

        if (updatedCount == 0) {
            throw new GlobalException(ErrorCode.EMPLOYEE_NOT_FOUND);
        }
    }
}