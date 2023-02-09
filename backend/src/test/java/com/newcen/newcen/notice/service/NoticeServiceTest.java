package com.newcen.newcen.notice.service;

import com.newcen.newcen.common.entity.BoardCommentIs;
import com.newcen.newcen.common.entity.BoardType;
import com.newcen.newcen.common.entity.UserEntity;
import com.newcen.newcen.notice.dto.request.NoticeCreateFileRequestDTO;
import com.newcen.newcen.notice.dto.request.NoticeCreateRequestDTO;
import com.newcen.newcen.notice.dto.request.NoticeUpdateRequestDTO;
import com.newcen.newcen.notice.dto.response.NoticeDetailResponseDTO;
import com.newcen.newcen.notice.dto.response.NoticeListResponseDTO;
import com.newcen.newcen.notice.dto.response.NoticeOneResponseDTO;
import com.newcen.newcen.notice.repository.NoticeRepository;
import com.newcen.newcen.users.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class NoticeServiceTest {

    @Autowired
    NoticeService noticeService;

    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("공지사항 목록 조회하면 3개이다")
    void showList() {

        // given

        // when
        NoticeListResponseDTO responseDTO = noticeService.retrieve();

        // then
        List<NoticeDetailResponseDTO> notices = responseDTO.getNotices();
        assertEquals(3, notices.size());

        System.out.println("========================");
        notices.forEach(System.out::println);
    }

    @Test
    @DisplayName("두번째 공지사항의 제목은 '공지사항2'이다.")
    void showOneList() {

        // given
        int index = 1;      // 두번째 공지사항) index = 1
        Long boardId = noticeService.retrieve().getNotices().get(index).getBoardId();

        // when
        NoticeOneResponseDTO responseDTO = noticeService.retrieveOne(boardId);

        // then
        List<NoticeDetailResponseDTO> notices = responseDTO.getNoticeDetails();
        assertEquals("공지사항2", notices.get(0).getBoardTitle());

        System.out.println("========================");
        notices.forEach(System.out::println);
    }

    @Test
    @DisplayName("관리자가 새로운 공지사항을 (title: 공지사항3, content: 내용3) 등록이 가능하다.")
    void createTest() {

        // given
        UserEntity user = userRepository.findById("402880e7862e5d3201862e5d3ab20000").get();   // 인사팀 user_id (ADMIN)

        NoticeCreateRequestDTO newNotice = NoticeCreateRequestDTO.builder()
                .boardTitle("공지사항3")
                .boardContent("내용3")
                .boardCommentIs(BoardCommentIs.ON)
                .build();

        // when
        NoticeOneResponseDTO noticeOneResponseDTO = noticeService.create(newNotice, user.getUserId());

        // then
        List<NoticeDetailResponseDTO> notices = noticeOneResponseDTO.getNoticeDetails();

        System.out.println("========================");
        notices.forEach(System.out::println);
    }

    @Test
    @DisplayName("사용자가 새로운 공지사항을 등록하면 오류가 발생해야 한다.")
    void createErrorTest() {

        // given
        UserEntity user = userRepository.findById("402880e7862e5d3201862e5d3ae30002").get();   // 이오이 user_id (MEMBER)

        NoticeCreateRequestDTO newNotice = NoticeCreateRequestDTO.builder()
                .boardTitle("에러 공지사항")
                .boardContent("에러 공지사항")
                .boardCommentIs(BoardCommentIs.ON)
                .build();

        // then
        assertThrows(RuntimeException.class, () -> {
            // when
            NoticeOneResponseDTO noticeOneResponseDTO = noticeService.create(newNotice, user.getUserId());
        });
    }

    @Test
    @DisplayName("인사팀 관리자가 첫번째 공지사항의 제목을 '제목 수정'으로, 내용을 '내용 수정'으로, 공지사항 댓글을 허용(boardCommentIs=ON) 해야한다")
    void updateTest() {

        // given
        UserEntity user = userRepository.findById("402880e7862e5d3201862e5d3ab20000").get();   // 인사팀 user_id (ADMIN)
        String newTitle = "제목 수정";
        String newContent = "내용 수정";

        NoticeUpdateRequestDTO updateRequestDTO = NoticeUpdateRequestDTO.builder()
                .boardTitle(newTitle)
                .boardWriter(user.getUserName())
                .boardContent(newContent)
                .boardCommentIs(BoardCommentIs.ON)
                .build();

        // when
        NoticeDetailResponseDTO targetNotice = noticeService.retrieve().getNotices().get(0);        // 0번 index : 1번째 data get
        System.out.println("targetNotice = " + targetNotice);
        
        NoticeOneResponseDTO responseDTO = noticeService.update(targetNotice.getBoardId(), updateRequestDTO, user.getUserId());
        System.out.println("responseDTO = " + responseDTO);

        // then
        List<NoticeDetailResponseDTO> notices = responseDTO.getNoticeDetails();

        System.out.println("========================");
        notices.forEach(System.out::println);
    }

    @Test
    @DisplayName("사용자가 새로운 공지사항을 수정하면 오류가 발생해야 한다.")
    void updateErrorTest() {

        // given
        UserEntity user = userRepository.findById("402880e7862e5d3201862e5d3ae30002").get();   // 이오이 user_id (MEMBER)
        String newTitle = "제목 수정";
        String newContent = "내용 수정";

        NoticeUpdateRequestDTO updateRequestDTO = NoticeUpdateRequestDTO.builder()
                .boardTitle(newTitle)
                .boardWriter(user.getUserName())
                .boardContent(newContent)
                .boardCommentIs(BoardCommentIs.ON)
                .build();

        // then
        assertThrows(RuntimeException.class, () -> {
            // when
            NoticeDetailResponseDTO targetNotice = noticeService.retrieve().getNotices().get(0);        // 0번 index : 1번째 data get
            noticeService.update(targetNotice.getBoardId(), updateRequestDTO, user.getUserId());
        });
    }

    @Test
    @DisplayName("3번째 공지사항을 삭제하면 2개의 공지사항만 남는다.")
    void deleteTest() {
        // given
        int index = 0;
        String userId = "402880e7862e5d3201862e5d3ab20000";

        // when
        Long deleteId = noticeService.retrieve().getNotices().get(index).getBoardId();  // 2번 index : 3번째 data get

        NoticeListResponseDTO deleteDTO = noticeService.delete(deleteId, userId);

        // then
        List<NoticeDetailResponseDTO> notices = deleteDTO.getNotices();
        assertEquals(2, notices.size());

        System.out.println("==========================");
        deleteDTO.getNotices().forEach(System.out::println);
    }

    // --- File Test --- //
    @Test
    @DisplayName("관리자가 2번째 공지사항에 파일 등록이 가능하다.")
    void createFileTest() {

        // given
        UserEntity user = userRepository.findById("402880e7862e5d3201862e5d3ab20000").get();   // 인사팀 user_id (ADMIN)

        String filePath = "test.md";

        Long boardId = noticeService.retrieve().getNotices().get(1).getBoardId();        // 2번째 공지사항 boardId

        NoticeCreateFileRequestDTO newFile = NoticeCreateFileRequestDTO.builder()
                .boardFilePath(filePath)
                .build();

        // when
        NoticeOneResponseDTO noticeOneResponseDTO = noticeService.createFile(boardId, newFile, user.getUserId());

        // then
        List<NoticeDetailResponseDTO> notices = noticeOneResponseDTO.getNoticeDetails();

        System.out.println("========================");
        notices.forEach(System.out::println);
    }

    @Test
    @DisplayName("사용자가 2번째 공지사항에 파일을 등록하면 오류가 발생해야 한다.")
    void createFileErrorTest() {

        // given
        UserEntity user = userRepository.findById("402880e7862e5d3201862e5d3ae30002").get();   // 이오이 user_id (MEMBER)
        String filePath = "error.md";

        Long boardId = noticeService.retrieve().getNotices().get(1).getBoardId();        // 2번째 공지사항 boardId

        NoticeCreateFileRequestDTO newFile = NoticeCreateFileRequestDTO.builder()
                .boardFilePath(filePath)
                .build();

        // then
        assertThrows(RuntimeException.class, () -> {
            // when
            noticeService.createFile(boardId, newFile, user.getUserId());
        });
    }
}