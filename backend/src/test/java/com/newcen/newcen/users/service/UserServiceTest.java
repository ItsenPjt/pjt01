package com.newcen.newcen.users.service;

import com.newcen.newcen.users.dto.request.UserSignUpRequestDTO;
import com.newcen.newcen.users.dto.response.UserSignUpResponseDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {

    @Autowired
    UserService userService;

    @Test
    @DisplayName("존재하지 않는 회원정보로 가입을 시도하면 RuntimeException이 발생해야 한다.")
    void nonexistentUserInfoTest() {
        // given
        UserSignUpRequestDTO dto = UserSignUpRequestDTO.builder()
                .userEmail("alsongdalsong@gmail.com")
                .userPassword("abc1234")
                .userName("강감찬")
                .validCode("QWERZX")
                .build();

        // when
//        UserService.create(dto);

        // then
        assertThrows(RuntimeException.class, () -> {
            userService.create(dto);    // when, then을 같이 실행 - create를 실행하면 error 발생 단언
        });

    }

    @Test
    @DisplayName("검증된 회원정보로 가입하면 회원가입에 성공해야 한다.")
    void createTest() {
        // given
        UserSignUpRequestDTO dto = UserSignUpRequestDTO.builder()
                .userEmail("postman@naver.com")
                .userPassword("abc1234")
                .userName("암호맨")
                .validCode("XY2baJQ")
                .build();

        // when
        UserSignUpResponseDTO responseDTO = userService.create(dto);

        // then
        System.out.println("====================");
        System.out.println("responseDTO = " + responseDTO);
        System.out.println("====================");
        assertEquals("암호맨", responseDTO.getUserName());

    }

}