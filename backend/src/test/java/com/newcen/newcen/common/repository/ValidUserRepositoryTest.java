package com.newcen.newcen.common.repository;

import com.newcen.newcen.common.entity.UserEntity;
import com.newcen.newcen.common.entity.ValidUserEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest
class ValidUserRepositoryTest {

    @Autowired
    ValidUserRepository validUserRepository;


    @Test
    @DisplayName("회원 이메일, 유저코드를 삽입해야한다.")
    void saveValidUserTest() {

        // given
        ValidUserEntity validUser = ValidUserEntity.builder()
                .validUserEmail("postman@naver.com")
                .validCode("XY2baJQ")
                .validActive(1)
                .build();

        // .build() 대신 Setter() 사용 방법
//        ValidUserEntity validUser = new ValidUserEntity();
//        validUser.setValidUserEmail("postman@naver.com");
//        validUser.setValidCode("XY2baJQ");

        // when
        ValidUserEntity saveValidUser = validUserRepository.save(validUser);

        // then
        assertNotNull(saveValidUser);

        System.out.println("==============================");
        // 해당 테스트 중 save 후 commit이 없기 때문에
        // 영속성 문제로 출력문에서는 validActive 값이 null로 나옴
        // 하지만 validActive는 default 값을 사용하기 때문에 DB값은 default값이 삽입 됨.
        System.out.println("saveValidUser = " + saveValidUser);
        System.out.println("==============================");

    }

    @Test
    @DisplayName("이메일 중복을 체크해야 한다.")
    void existByValidUserEmailTest() {
        // given
        String email = "dagil2@naver.com";

        //when
        boolean flag = validUserRepository.existsByValidUserEmail(email);

        //then
        assertFalse(flag);
    }

    @Test
    @DisplayName("기존에 등록되지 않은 이메일과 인증코드를 비교해서 false면 성공해야 한다.")
    void existsByValidUserEmailAndValidCodeAndValidActiveTest() {
        // given
        String email = "dagil2@naver.com";
        String code = "PA23VLK";

        //when
//        ValidUserEntity flag = validUserRepository.findByValidUserEmailAndValidCode(email, code);
        boolean flag = validUserRepository.existsByValidUserEmailAndValidCodeAndValidActive(email, code, 1);

        //then
//        assertNotNull(flag);
        assertFalse(flag);

    }

    @Test
    @DisplayName("가입 시 active 값이 1인 계정에 대해 active 값을 2로 변경해야 한다.")
    @Transactional
    void updateSetActiveTest() {
        // given
        String email = "postman@naver.com";
        String code = "XY2baJQ";
        int active = 1;

        // when
        validUserRepository.updateSetActive(email, code);

        ValidUserEntity updatedActive =
                validUserRepository.findByValidUserEmailAndValidCode(email, code);

        // then
        assertEquals(2, updatedActive.getValidActive());

        System.out.println("====================");
        System.out.println("updatedActive = " + updatedActive);
        System.out.println("====================");

    }

    @Test
    @DisplayName("기존에 가입되어있는 회원의 인증코드를 조회하면 true를 반환해야 한다.")
    void existsByUserEmailAndUserNameTest() {
        // given
        String validCode = "XY2baJQ";

        // when
        boolean userCode = validUserRepository.existsByValidCode(validCode);

        // then
        assertTrue(userCode);

    }

    @Test
    @DisplayName("삭제하려는 회원의 Email로 validUserId를 조회해야한다.")
    void findByValidUserIdTest() {
        // given
        String email = "postman@naver.com";

        // when
        ValidUserEntity targetId = validUserRepository.findByValidUserId(email);

        // then
        assertNotEquals("402880b6862fa0f601862fa101410123", targetId);

    }

    @Test
    @DisplayName("조회된 회원 UUID로 회원 정보를 삭제해야 한다.")
    @Transactional
    void deleteByValidUserIdTest() {
        // given
        String id = "402880b6862fa0f601862fa101410000";

        // when
        validUserRepository.deleteById(id);

        // then
        Assertions.assertFalse(validUserRepository.existsById(id));

    }


}