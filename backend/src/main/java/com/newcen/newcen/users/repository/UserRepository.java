package com.newcen.newcen.users.repository;

import com.newcen.newcen.common.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<UserEntity, String> {

    // 이메일로 회원 조회
    // select * from user where email=?
//    UserEntity findByEmail(String email);

//    // 이메일 중복 검사
//    // select count(*) from user where email=?
//    // @Query("select count(*) from UserEntity u where u.email=?1"
//    boolean existsByEmail(String email);

}
