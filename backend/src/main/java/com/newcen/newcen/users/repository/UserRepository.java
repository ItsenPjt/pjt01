package com.newcen.newcen.users.repository;

import com.newcen.newcen.common.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, String> {

    // 이메일로 회원 조회
    // select * from user where email=?
    UserEntity findByUserEmail(String email);

    // 이메일 중복 검사
    // select count(*) from user where email=?
    // @Query("select count(*) from UserEntity u where u.email=?1"
    boolean existsByUserEmail(String email);

    // 회원명으로 회원 조회 (메세지)
    List<UserEntity> findByUserNameContains(String userName);

    // 유저 아이디로 회원 조회 (메세지)
    Optional<UserEntity> findByUserId(String userId);

    // UUID로 회원정보 삭제
    void deleteById(String UserId);

    // 삭제한 회원 UUID로 email 존재 여부 조회
    boolean existsById(String UserId);

    // 삭제한 회원 Email 조회
    @Query("SELECT u.userEmail FROM UserEntity u WHERE u.userId =:userId")
    String selectUserEmail(@Param("userId") String userId);

    // 익명 사용자 비밀번호 찾기 시 회원정보 존재 여부 조회
    boolean existsByUserEmailAndUserName(String email, String name);

}