package com.hr_handlers.employee.repository.custom;

import com.hr_handlers.employee.dto.request.EmpUpdateRequestDto;
import com.hr_handlers.employee.entity.ProfileImage;
import com.hr_handlers.employee.repository.ProfileImageRepository;
import com.hr_handlers.global.exception.ErrorCode;
import com.hr_handlers.global.exception.GlobalException;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAUpdateClause;
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
    public void updateEmp(String empNo, EmpUpdateRequestDto requestDto, String newProfileImageUrl) {
        // 프로필 이미지 변경 및 생성
        ProfileImage profileImage = null;
        if (newProfileImageUrl != null) {
            profileImage = ProfileImage.builder()
                    .profileImageUrl(newProfileImageUrl)
                    .build();
            profileImageRepository.save(profileImage);
        }
        // 비밀번호 암호화 처리
//        String encryptedPassword = requestDto.getPassword() != null && !requestDto.getPassword().isEmpty()
//                ? passwordEncoder.encode(requestDto.getPassword())
//                : null;

        JPAUpdateClause updateClause = jpaQueryFactory
                .update(employee)
                .where(employee.empNo.eq(empNo))
                .set(employee.email, requestDto.getEmail())
                .set(employee.phone, requestDto.getPhone())
                .set(employee.introduction, requestDto.getIntroduction());

        // 프로필 이미지가 있을 경우 업데이트
        if (profileImage != null) {
            updateClause.set(employee.profileImage, profileImage);
        }

        // 비밀번호가 제공된 경우에만 업데이트
//        if (encryptedPassword != null) {
//            updateClause.set(employee.password, encryptedPassword);
//        }

        long updatedCount = updateClause.execute();

        entityManager.flush();
        entityManager.close();

        if (updatedCount == 0) {
            throw new GlobalException(ErrorCode.EMPLOYEE_NOT_FOUND);
        }
    }
}