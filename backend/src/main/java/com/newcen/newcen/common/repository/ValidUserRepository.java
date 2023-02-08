package com.newcen.newcen.common.repository;

import com.newcen.newcen.common.entity.ValidUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface ValidUserRepository extends JpaRepository<ValidUserEntity, String> {

    // 이메일로 회원 조회
    ValidUserEntity findByValidUserEmail(String email);

    // 이메일 중복 검사
    boolean existsByValidUserEmail(String email);

    // 유저 가입 시 등록정보 ValidUserEntitiy 타입으로 비교
    ValidUserEntity findByValidUserEmailAndValidCode(String email, String code);

    // 유저 가입 시 등록정보 boolean 타입으로 비교
    boolean existsByValidUserEmailAndValidCode(String email, String code);

}
