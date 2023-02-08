package com.newcen.newcen.users.repository;

import com.newcen.newcen.common.entity.UserEntity;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<UserEntity, String> {

    // 이메일로 회원 조회
    // select * from user where email=?
    UserEntity findByUserEmail(String email);

    // 이메일 중복 검사
    // select count(*) from user where email=?
    // @Query("select count(*) from UserEntity u where u.email=?1"
    boolean existsByUserEmail(String email);

    // 회원명으로 회원 조회 (메세지)
    @Query("select ")
    List<UserEntity> findByUserNameContains(String userName);

    // 유저 아이디로 회원 조회 (메세지)
    UserEntity findByUserId(String userId);


}
