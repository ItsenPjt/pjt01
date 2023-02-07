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

    @Test
    @DisplayName("회원가입에 성공해야 한다.")
//    @Transactional
//    @Rollback
    void saveTest() {
        // given
        UserEntity user = UserEntity.builder()
                .userEmail("postman@naver.com")
                .userPassword("1234")
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

}

