package com.newcen.newcen.users.repository;

import com.newcen.newcen.common.entity.UserEntity;
import com.newcen.newcen.common.entity.UserRole;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;


    /**
     * 해당 테스트 실행 전 user 테이블 데이터 삭제 후 진행 필요
     */
    @Test
    @DisplayName("회원가입에 성공해야 한다.")
//    @Transactional
//    @Rollback
    void saveTest() {
        // given
        UserEntity user = UserEntity.builder()
                .userEmail("postman@naver.com")
                .userPassword("abc1234")
                .userName("강감찬")
                .build();

        // when
        UserEntity savedUser = userRepository.save(user);

        // then
        assertNotNull(savedUser);

    }

    @Test
    @DisplayName("이메일로 회원을 조회해야 한다.")
    void findByUserEmailTest() {
        // given
        String email = "postman@naver.com";

        // when
        UserEntity foundUser = userRepository.findByUserEmail(email);

        // then
        assertEquals("강감찬", foundUser.getUserName());

    }

    @Test
    @DisplayName("이메일 중복을 체크해야 한다.")
    void existEmailTest() {
        // given
        String email = "dagil2@naver.com";

        //when
        boolean flag = userRepository.existsByUserEmail(email);

        //then
        assertFalse(flag);
    }

    @Test
    @DisplayName("기존에 가입되어있는 회원정보로 조회하면 true를 반환해야 한다.")
    void existsByUserEmailAndUserNameTest() {
        // given
        String email = "postman@naver.com";
        String name = "암호맨";

        // when
        boolean existsUser = userRepository.existsByUserEmailAndUserName(email, name);

        // then
        assertTrue(existsUser);

    }

    @Test
    @DisplayName("UUID로 회원 Email을 조회해야 한다.")
    @Transactional
    void selectUserEmailTest() {
        // given
        String userId = "402880b6862ff68b01862ff696530000";

        // when
        String userEmail = userRepository.selectUserEmail(userId);

        // then
        assertEquals("postman@naver.com", userEmail);
    }

}

