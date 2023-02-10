package com.newcen.newcen.common.repository;

import com.newcen.newcen.common.entity.ValidUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ValidUserRepository extends JpaRepository<ValidUserEntity, String> {

    // 이메일로 회원 조회
    ValidUserEntity findByValidUserEmail(String email);

    // 이메일 중복 검사
    boolean existsByValidUserEmail(String email);

    // 유저 가입 시 등록정보 ValidUserEntitiy 타입으로 비교
    ValidUserEntity findByValidUserEmailAndValidCode(String email, String code);

    // 유저 가입 시 등록정보 boolean 타입으로 비교
    boolean existsByValidUserEmailAndValidCodeAndValidActive(String email, String code, int active);

    // 유저 가입 시 active 기본값 변경
    @Modifying
    @Query("update ValidUserEntity u set u.validActive = 2 where u.validUserEmail = :validUserEmail And u.validCode = :validCode")
    void updateSetActive(@Param("validUserEmail") String email, @Param("validCode") String validCode);

    // 익명 사용자 비밀번호 찾기 시 인증코드 존재 여부 조회
    boolean existsByValidCode(String validCode);

    // 회원 삭제를 위해 email 로 회원 id 조회
    ValidUserEntity findByValidUserId(String email);

    // 조회된 회원 id로 회원삭제(탈퇴)
    void deleteById(String validUserId);

    // 회원 Email로 회원정보 삭제
    void deleteByValidUserEmail(String email);

    // validUserId 삭제 여부 조회
    boolean existsById(String validUserId);

    // 삭제된 회원 UUID 조회
    Optional<ValidUserEntity> findById(String validUserId);


}