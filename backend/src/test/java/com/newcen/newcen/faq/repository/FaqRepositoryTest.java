package com.newcen.newcen.faq.repository;

import com.newcen.newcen.common.entity.*;
import com.newcen.newcen.users.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FaqRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    FaqRepository faqRepository;


    // 테스트 전 일반 회원과 관리자 생성
    @BeforeEach
    void saveUserBeforeTest() {
        UserEntity user = UserEntity.builder()
                .userEmail("kim@naver.com")
                .userPassword("1111")
                .userName("김철수")
                .userRole(UserRole.MEMBER)
                .build();        
        
        UserEntity admin = UserEntity.builder()
                .userEmail("admin@naver.com")
                .userPassword("admin")
                .userName("관리자")
                .userRole(UserRole.ADMIN)
                .build();

        userRepository.save(user);
        userRepository.save(admin);
    }


    @Test
    @DisplayName("회원은 FAQ에 글을 등록 할 수 없다")
    @Transactional
    void saveFaqUser() {

        // given
        UserEntity user = userRepository.findByUserEmail("kim@naver.com");

        // when
        boolean isAdmin = user.getUserRole().equals(UserRole.ADMIN);

        // then
        Assertions.assertFalse(isAdmin);

    }

    @Test
    @DisplayName("관리자는 FAQ에 글을 등록할 수 있다")
    @Transactional
    void saveFaqAdmin() {

        // given
        UserEntity admin = userRepository.findByUserEmail("admin@naver.com");

        // when
        boolean isAdmin = admin.getUserRole().equals(UserRole.ADMIN);

        BoardEntity faq = BoardEntity.builder()
                .boardType(BoardType.FAQ)
                .boardTitle("자주 묻는 질문 1번 제목")
                .boardContent("1번 내용입니다")
                .boardWriter(admin.getUserName())
                .boardCommentIs(BoardCommentIs.OFF)
                .userId(admin.getUserId())
                .build();

        BoardEntity savedFaq = faqRepository.save(faq);


        // then
        Assertions.assertTrue(isAdmin);
        Assertions.assertEquals("관리자", savedFaq.getBoardWriter());

    }



}