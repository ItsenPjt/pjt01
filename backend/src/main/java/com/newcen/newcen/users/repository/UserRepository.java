package com.newcen.newcen.users.repository;

import com.newcen.newcen.common.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<UserEntity, String> {

    // 이메일로 회원 조회
    // select * from user where email=?
    UserEntity findByUserEmail(String email);

    // 이메일 중복 검사
    // select count(*) from user where email=?
    // @Query("select count(*) from UserEntity u where u.email=?1"
    boolean existsByUserEmail(String email);

    // 메세지 회원명으로 회원 조회
    List<UserEntity> findByUserName(String userName);

    // 유저 가입 정보 비교



}
