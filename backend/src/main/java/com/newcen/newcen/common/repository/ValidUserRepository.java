package com.newcen.newcen.common.repository;

import com.newcen.newcen.common.entity.ValidUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ValidUserRepository extends JpaRepository<ValidUserEntity, String> {

    // 이메일로 회원 조회
    ValidUserEntity findByValidUserEmail(String email);

    // 이메일 중복 검사
    boolean existsByValidUserEmail(String email);


}
