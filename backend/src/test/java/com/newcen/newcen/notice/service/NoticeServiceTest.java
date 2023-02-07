package com.newcen.newcen.notice.service;

import com.newcen.newcen.common.entity.BoardCommentIs;
import com.newcen.newcen.common.entity.BoardType;
import com.newcen.newcen.common.entity.UserEntity;
import com.newcen.newcen.notice.dto.request.NoticeCreateRequestDTO;
import com.newcen.newcen.notice.dto.response.NoticeDetailResponseDTO;
import com.newcen.newcen.notice.dto.response.NoticeListResponseDTO;
import com.newcen.newcen.users.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class NoticeServiceTest {

    @Autowired
    NoticeService noticeService;

    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("공지사항 목록 조회하면 4개이다")
    void showList() {

        // given

        // when
        NoticeListResponseDTO responseDTO = noticeService.retrieve();

        // then
        List<NoticeDetailResponseDTO> notices = responseDTO.getNotices();
        assertEquals(4, notices.size());

        System.out.println("========================");
        notices.forEach(System.out::println);
    }

    @Test
    @DisplayName("관리자가 새로운 공지사항을 등록하면 생성되는 리스트는, 공지사항이 5개 들어있어야 한다.")
    void createTest() {
        UserEntity user = userRepository.findByUserEmail("test@naver.com");
        // userRole 로 가져오기

        // given
        NoticeCreateRequestDTO newNotice = NoticeCreateRequestDTO.builder()
                .boardTitle("service 공지")
                .boardContent("service 내용")
                .boardCommentIs(BoardCommentIs.OFF)
                .build();

        // when
        NoticeListResponseDTO responseDTO = noticeService.create(newNotice, user);

        // then
        List<NoticeDetailResponseDTO> notices = responseDTO.getNotices();
        assertEquals(5, notices.size());

        System.out.println("========================");
        notices.forEach(System.out::println);
    }
}