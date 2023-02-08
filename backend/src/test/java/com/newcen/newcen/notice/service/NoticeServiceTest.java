package com.newcen.newcen.notice.service;

import com.newcen.newcen.common.entity.BoardCommentIs;
import com.newcen.newcen.common.entity.BoardType;
import com.newcen.newcen.common.entity.UserEntity;
import com.newcen.newcen.notice.dto.request.NoticeCreateRequestDTO;
import com.newcen.newcen.notice.dto.request.NoticeUpdateRequestDTO;
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
    @DisplayName("공지사항 목록 조회하면 2개이다")
    void showList() {

        // given

        // when
        NoticeListResponseDTO responseDTO = noticeService.retrieve();

        // then
        List<NoticeDetailResponseDTO> notices = responseDTO.getNotices();
        assertEquals(2, notices.size());

        System.out.println("========================");
        notices.forEach(System.out::println);
    }

    @Test
    @DisplayName("관리자가 새로운 공지사항을 등록하면 생성되는 리스트는, 공지사항이 3개 들어있어야 한다.")
    void createTest() {

        UserEntity user = userRepository.findById("f7402bfa862c193a01862c1942ae0000").get();   // 인사팀 user_id

        // given
        NoticeCreateRequestDTO newNotice = NoticeCreateRequestDTO.builder()
                .boardTitle("공지사항3")
                .boardContent("공지사항3")
                .boardCommentIs(BoardCommentIs.ON)
                .build();

        // when
        NoticeListResponseDTO responseDTO = noticeService.create(newNotice, user.getUserId());

        // then
        List<NoticeDetailResponseDTO> notices = responseDTO.getNotices();
        assertEquals(3, notices.size());

        System.out.println("========================");
        notices.forEach(System.out::println);
    }

    @Test
    @DisplayName("사용자가 새로운 공지사항을 등록하면 오류가 발생한다")
    void createErrorTest() {
        UserEntity user = userRepository.findById("f7402bfa862c193a01862c1942dd0002").get();   // 이오이 user_id

        // given
        NoticeCreateRequestDTO newNotice = NoticeCreateRequestDTO.builder()
                .boardTitle("에러 공지사항")
                .boardContent("에러 공지사항")
                .boardCommentIs(BoardCommentIs.ON)
                .build();

        // when
        NoticeListResponseDTO responseDTO = noticeService.create(newNotice, user.getUserId());

        // then
        List<NoticeDetailResponseDTO> notices = responseDTO.getNotices();
        assertEquals(3, notices.size());

        System.out.println("========================");
        notices.forEach(System.out::println);
    }

    @Test
    @DisplayName("두번째 공지사항의 제목을 '수정수정' 으로 수정하고, 공지사항 댓글을 허용(boardCommentIs=ON) 해야한다")
    void updateTest() {
        // given
        String newTitle = "수정수정";

        NoticeUpdateRequestDTO updateRequestDTO = NoticeUpdateRequestDTO.builder()
                .boardTitle(newTitle)
                .boardCommentIs(BoardCommentIs.ON)
                .build();

        // when
        NoticeDetailResponseDTO targetNotice = noticeService.retrieve().getNotices().get(1);// 1번 index : 2번째 data get

        NoticeListResponseDTO responseDTO = noticeService.update(targetNotice.getBoardId(), updateRequestDTO);

        // then
        assertEquals("수정수정", responseDTO.getNotices().get(1).getBoardTitle());
            // 댓글 허용 여부

        System.out.println("==========================");
        responseDTO.getNotices().forEach(System.out::println);
    }

    @Test
    @DisplayName("3번째 공지사항을 삭제해야 한다")
    void deleteTest() {
        // given
        int index = 2;

        // when
        Long deleteId = noticeService.retrieve().getNotices().get(index).getBoardId();// 2번 index : 3번째 data get

        NoticeListResponseDTO deleteDTO = noticeService.delete(deleteId);

        // then
        System.out.println("==========================");
        deleteDTO.getNotices().forEach(System.out::println);
    }
}