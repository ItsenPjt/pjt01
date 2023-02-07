package com.newcen.newcen.common.repository;

import com.newcen.newcen.common.entity.ValidUserEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

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

}