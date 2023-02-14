package com.newcen.newcen.faq.service;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.newcen.newcen.common.entity.*;
import com.newcen.newcen.faq.dto.request.FaqSaveRequestDTO;
import com.newcen.newcen.faq.dto.request.FaqUpdateRequestDTO;
import com.newcen.newcen.faq.dto.response.FaqDetailResponseDTO;
import com.newcen.newcen.faq.dto.response.FaqResponseDTO;
import com.newcen.newcen.users.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.config.web.server.AbstractServerWebExchangeMatcherRegistry;
import org.springframework.security.crypto.encrypt.AesBytesEncryptor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FaqServiceTest {

    @Autowired
    FaqService faqService;

    @Autowired
    UserRepository userRepository;

    @BeforeEach
    @Transactional
    void saveUserBeforeTest() {

        UserEntity user = UserEntity.builder()
                .userEmail("serviceUser@naver.com")
                .userPassword("1111")
                .userName("서비스 회원")
                .userRole(UserRole.MEMBER)
                .build();

        UserEntity admin = UserEntity.builder()
                .userEmail("serviceAdmin@naver.com")
                .userPassword("admin")
                .userName("서비스 관리자")
                .userRole(UserRole.ADMIN)
                .build();

        userRepository.save(user);
        userRepository.save(admin);

    }

    @Test
    @DisplayName("회원이 FAQ를 작성하려하면 RunTimeException이 발생한다")
    void saveFaqErrorTest() {

        // given
        UserEntity user = userRepository.findByUserEmail("serviceUser@naver.com");

        // when

        // then
        Assertions.assertThrows(RuntimeException.class, () -> {
            FaqSaveRequestDTO faq = FaqSaveRequestDTO.builder()
                    .boardTitle("FAQ Service 1번 제목")
                    .boardContent("1번")
                    .build();
            faqService.faqSave(user.getUserId(), faq);
        });
    }

    @Test
    @DisplayName("관리자는 FAQ를 작성할 수 있다")
    @Transactional
    void saveFaqSuccessTest() {
        // given
        UserEntity user = userRepository.findByUserEmail("serviceAdmin@naver.com");
        
        // When 
        FaqSaveRequestDTO faq = FaqSaveRequestDTO.builder()
                .boardTitle("FAQ Service 1번 제목")
                .boardContent("1번")
                .build();
//        List<FaqResponseDTO> faqList = faqService.faqSave(user.getUserId(), faq);
        boolean faqList = faqService.faqSave(user.getUserId(), faq);
        // then
//        Assertions.assertEquals(1, faqList.size());
//        Assertions.assertEquals("서비스 관리자", faqList.get(0).getBoardWriter());
    }

    @Test
    @DisplayName("FAQ에는 글이 2개 등록되어있다")
    @Transactional
    void faqListTest() {

        // given
        UserEntity user = userRepository.findByUserEmail("serviceAdmin@naver.com");

        FaqSaveRequestDTO faq = FaqSaveRequestDTO.builder()
                .boardTitle("FAQ Service 1번 제목")
                .boardContent("1번")
                .build();

        FaqSaveRequestDTO faq2 = FaqSaveRequestDTO.builder()
                .boardTitle("FAQ Service 2번 제목")
                .boardContent("2번")
                .build();

        faqService.faqSave(user.getUserId(), faq);
        faqService.faqSave(user.getUserId(), faq2);

        // when
        List<FaqResponseDTO> faqList = faqService.faqList();

        // then
        Assertions.assertEquals(2, faqList.size());
        Assertions.assertEquals("FAQ Service 2번 제목", faqList.get(1).getBoardTitle());
    }

    @Test
    @DisplayName("FAQ 목록의 첫번째 글 내용은 1번이다")
    @Transactional
    void faqDetailTest() {

        // given
        UserEntity user = userRepository.findByUserEmail("serviceAdmin@naver.com");

        FaqSaveRequestDTO faq = FaqSaveRequestDTO.builder()
                .boardTitle("FAQ Service 1번 제목")
                .boardContent("1번")
                .build();

        faqService.faqSave(user.getUserId(), faq);

        // when
        List<FaqResponseDTO> faqList = faqService.faqList();
        Long faqId = faqList.get(0).getBoardId();

        FaqDetailResponseDTO faqDetail = faqService.faqDetail(faqId);

        // then
        Assertions.assertEquals("1번", faqDetail.getBoardContent());
    }

    @Test
    @DisplayName("첫번째 FAQ의 제목을 수정FAQ로 변경한다")
    @Transactional
    void faqUpdateTest() {

        // given
        UserEntity user = userRepository.findByUserEmail("serviceAdmin@naver.com");

        FaqSaveRequestDTO faq = FaqSaveRequestDTO.builder()
                .boardTitle("FAQ Service 1번 제목")
                .boardContent("1번")
                .build();

        faqService.faqSave(user.getUserId(), faq);

        // when
        List<FaqResponseDTO> faqList = faqService.faqList();
        Long faqId = faqList.get(0).getBoardId();

        FaqUpdateRequestDTO newFaq = FaqUpdateRequestDTO.builder()
                .boardId(faqId)
                .boardTitle("수정FAQ")
                .boardContent("수정~~")
                .build();

//        FaqDetailResponseDTO faqDetail = faqService.faqUpdate(user.getUserId(), newFaq);

        // then
//        Assertions.assertEquals("수정FAQ", faqDetail.getBoardTitle());
//        Assertions.assertNotNull(faqDetail.getBoardUpdatedate());
    }


    @Test
    @DisplayName("첫번째 FAQ를 삭제해야 한다")
    @Transactional
    void faqDeleteTest() {
        // given
        UserEntity user = userRepository.findByUserEmail("serviceAdmin@naver.com");

        FaqSaveRequestDTO faq = FaqSaveRequestDTO.builder()
                .boardTitle("FAQ Service 1번 제목")
                .boardContent("1번")
                .build();

        FaqSaveRequestDTO faq2= FaqSaveRequestDTO.builder()
                .boardTitle("FAQ Service 2번 제목")
                .boardContent("2번")
                .build();

        faqService.faqSave(user.getUserId(), faq);
        faqService.faqSave(user.getUserId(), faq2);

        List<FaqResponseDTO> faqList = faqService.faqList();
        Long faqId = faqList.get(0).getBoardId();


        // when
//        List<FaqResponseDTO> newFaqList = faqService.faqDelete(user.getUserId(), faqId);

        // then
//        Assertions.assertEquals(1, newFaqList.size());

    }



}