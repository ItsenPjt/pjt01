package com.newcen.newcen.users.service;

import com.newcen.newcen.common.entity.UserEntity;
import com.newcen.newcen.users.dto.request.AnonymousReviseRequestDTO;
import com.newcen.newcen.users.dto.request.UserModifyRequestDTO;
import com.newcen.newcen.users.dto.request.UserSignUpRequestDTO;
import com.newcen.newcen.users.dto.response.AnonymousReviseResponseDTO;
import com.newcen.newcen.users.dto.response.LoginResponseDTO;
import com.newcen.newcen.users.dto.response.UserModifyResponseDTO;
import com.newcen.newcen.users.dto.response.UserSignUpResponseDTO;
import com.newcen.newcen.users.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
// JUint 테스트 순서 지정 각 테스트 항목에 @Order() 로 순서지정
class UserServiceTest {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Test
    @Order(1)
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
    @Order(2)
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
        assertEquals("암호맨", responseDTO.getUserName());

    }

    @Test
    @Order(3)
    @DisplayName("회원 가입된 회원을 조회해야 한다.")
    @Transactional
    void selectUserEmailTest() {
        // given
        String email = "postman@naver.com";

        // when
        UserEntity selectUser = userRepository.findByUserEmail(email);

        // then
        assertEquals("암호맨", selectUser.getUserName());

        System.out.println("selectUser = " + selectUser);

    }

    @Test
    @Order(4)
    @DisplayName("정확한 정보로 로그인을 시도하면 회원정보가 반환되어야 한다.")
    void loginTest() {
        // given
        String email = "postman@naver.com";
        String password = "abc1234";

        // when
        LoginResponseDTO loginUser = userService.getByCredentials(email, password);

        // then
        assertEquals("암호맨", loginUser.getUserName());

    }

    @Test
    @Order(5)
    @DisplayName("회원 테이블에 존재하는 userId값이면 비밀번호를 암호화하여 수정해야 한다.")
    void passwordModifyTest() {
        // given
        UserModifyRequestDTO dto = UserModifyRequestDTO.builder()
                .userPassword("abc1234")
                .build();

        String userId = "402880b6862ff68b01862ff696530000";

        // when
        UserModifyResponseDTO modifyUser = userService.update(userId, dto);

        // then
        assertEquals("암호맨", modifyUser.getUserName());

    }

    @Test
    @Order(6)
    @DisplayName("존재하는 이메일, 이름, 인증코드면 비밀번호를 암호화하여 수정해야 한다.")
    void anonymousReviseTest() {
        // given
        AnonymousReviseRequestDTO anonymousDto = AnonymousReviseRequestDTO.builder()
                .userEmail("postman@naver.com")
                .userPassword("abc1234")
                .userName("암호맨")
                .validCode("XY2baJQ")
                .build();

        // when
        AnonymousReviseResponseDTO useUser = userService.update(anonymousDto);

        // then
        assertEquals("암호맨", anonymousDto.getUserName());
    }

}

