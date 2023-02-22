//package com.newcen.newcen.notice.service;
//
//import com.newcen.newcen.common.entity.BoardCommentIs;
//import com.newcen.newcen.common.entity.BoardFileEntity;
//import com.newcen.newcen.common.entity.UserEntity;
//import com.newcen.newcen.notice.dto.request.NoticeCreateFileRequestDTO;
//import com.newcen.newcen.notice.dto.request.NoticeCreateRequestDTO;
//import com.newcen.newcen.notice.dto.request.NoticeUpdateFileRequestDTO;
//import com.newcen.newcen.notice.dto.request.NoticeUpdateRequestDTO;
//import com.newcen.newcen.notice.dto.response.NoticeDetailResponseDTO;
//import com.newcen.newcen.notice.dto.response.NoticeListResponseDTO;
//import com.newcen.newcen.notice.dto.response.NoticeOneResponseDTO;
//import com.newcen.newcen.users.repository.UserRepository;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//class NoticeServiceTest {
//
//    @Autowired
//    NoticeService noticeService;
//
//    @Autowired
//    UserRepository userRepository;
//
//    @Test
//    @DisplayName("공지사항 목록 조회하면 3개이다")
//    void showList() {
//
//        // given
//
//        // when
//        NoticeListResponseDTO responseDTO = noticeService.retrieve();
//
//        // then
//        List<NoticeDetailResponseDTO> notices = responseDTO.getNotices();
//        assertEquals(3, notices.size());
//
//        System.out.println("========================");
//        notices.forEach(System.out::println);
//    }
//
//    @Test
//    @DisplayName("두번째 공지사항의 제목은 '공지사항2'이다.")
//    void showOneList() {
//
//        // given
//        int index = 1;      // 두번째 공지사항) index = 1
//        Long boardId = noticeService.retrieve().getNotices().get(index).getBoardId();
//
//        // when
//        NoticeOneResponseDTO responseDTO = noticeService.retrieveOne(boardId);
//
//        // then
//        List<NoticeDetailResponseDTO> notices = responseDTO.getNoticeDetails();
//        assertEquals("공지사항2", notices.get(0).getBoardTitle());
//
//        System.out.println("========================");
//        notices.forEach(System.out::println);
//    }
//
//    @Test
//    @DisplayName("관리자가 새로운 공지사항을 (title: 공지사항3, content: 내용3) 등록이 가능하다.")
//    void createTest() {
//
//        // given
//        UserEntity user = userRepository.findById("402880e7862e5d3201862e5d3ab20000").get();   // 인사팀 user_id (ADMIN)
//
//        NoticeCreateRequestDTO newNotice = NoticeCreateRequestDTO.builder()
//                .boardTitle("공지사항3")
//                .boardContent("내용3")
//                .boardCommentIs(BoardCommentIs.ON)
//                .build();
//
//        // when
//        NoticeOneResponseDTO noticeOneResponseDTO = noticeService.create(newNotice, user.getUserId());
//
//        // then
//        List<NoticeDetailResponseDTO> notices = noticeOneResponseDTO.getNoticeDetails();
//
//        System.out.println("========================");
//        notices.forEach(System.out::println);
//    }
//
//    @Test
//    @DisplayName("사용자가 새로운 공지사항을 등록하면 오류가 발생해야 한다.")
//    void createErrorTest() {
//
//        // given
//        UserEntity user = userRepository.findById("402880e7862e5d3201862e5d3ae30002").get();   // 이오이 user_id (MEMBER)
//
//        NoticeCreateRequestDTO newNotice = NoticeCreateRequestDTO.builder()
//                .boardTitle("에러 공지사항")
//                .boardContent("에러 공지사항")
//                .boardCommentIs(BoardCommentIs.ON)
//                .build();
//
//        // then
//        assertThrows(RuntimeException.class, () -> {
//            // when
//            NoticeOneResponseDTO noticeOneResponseDTO = noticeService.create(newNotice, user.getUserId());
//        });
//    }
//
//    @Test
//    @DisplayName("인사팀 관리자가 첫번째 공지사항의 제목을 '제목 수정'으로, 내용을 '내용 수정'으로, 공지사항 댓글을 허용(boardCommentIs=ON) 해야한다")
//    void updateTest() {
//
//        // given
//        UserEntity user = userRepository.findById("402880e7862e5d3201862e5d3ab20000").get();   // 인사팀 user_id (ADMIN)
//        String newTitle = "제목 수정";
//        String newContent = "내용 수정";
//
//        NoticeUpdateRequestDTO updateRequestDTO = NoticeUpdateRequestDTO.builder()
//                .boardTitle(newTitle)
//                .boardWriter(user.getUserName())
//                .boardContent(newContent)
//                .boardCommentIs(BoardCommentIs.ON)
//                .build();
//
//        // when
//        NoticeDetailResponseDTO targetNotice = noticeService.retrieve().getNotices().get(0);        // 0번 index : 1번째 data get
//        System.out.println("targetNotice = " + targetNotice);
//
//        NoticeOneResponseDTO responseDTO = noticeService.update(targetNotice.getBoardId(), updateRequestDTO, user.getUserId());
//        System.out.println("responseDTO = " + responseDTO);
//
//        // then
//        List<NoticeDetailResponseDTO> notices = responseDTO.getNoticeDetails();
//
//        System.out.println("========================");
//        notices.forEach(System.out::println);
//    }
//
//    @Test
//    @DisplayName("사용자가 새로운 공지사항을 수정하면 오류가 발생해야 한다.")
//    void updateErrorTest() {
//
//        // given
//        UserEntity user = userRepository.findById("402880e7862e5d3201862e5d3ae30002").get();   // 이오이 user_id (MEMBER)
//        String newTitle = "제목 수정";
//        String newContent = "내용 수정";
//
//        NoticeUpdateRequestDTO updateRequestDTO = NoticeUpdateRequestDTO.builder()
//                .boardTitle(newTitle)
//                .boardWriter(user.getUserName())
//                .boardContent(newContent)
//                .boardCommentIs(BoardCommentIs.ON)
//                .build();
//
//        // then
//        assertThrows(RuntimeException.class, () -> {
//            // when
//            NoticeDetailResponseDTO targetNotice = noticeService.retrieve().getNotices().get(0);        // 0번 index : 1번째 data get
//            noticeService.update(targetNotice.getBoardId(), updateRequestDTO, user.getUserId());
//        });
//    }
//
//    @Test
//    @DisplayName("3번째 공지사항을 삭제하면 2개의 공지사항만 남는다.")
//    void deleteTest() {
//        // given
//        int index = 0;
//        String userId = "402880e7862e5d3201862e5d3ab20000";
//
//        // when
//        Long deleteId = noticeService.retrieve().getNotices().get(index).getBoardId();  // 2번 index : 3번째 data get
//
//        boolean delete = noticeService.delete(deleteId, userId);
//
//        // then
//        assertEquals(true, delete);
//    }
//
//    // --- File Test --- //
//    @Test
//    @DisplayName("관리자가 2번째 공지사항에 파일 등록이 가능하다.")
//    void createFileTest() {
//
//        // given
//        UserEntity user = userRepository.findById("402880e7863388c301863388cce40000").get();   // 암호맨 user_id (ADMIN)
//
//        String filePath = "test.md";
//
//        Long boardId = noticeService.retrieve().getNotices().get(1).getBoardId();        // 2번째 공지사항 boardId
//
//        NoticeCreateFileRequestDTO newFile = NoticeCreateFileRequestDTO.builder()
//                .boardFilePath(filePath)
//                .build();
//
//        // when
//        NoticeOneResponseDTO noticeOneResponseDTO = noticeService.createFile(boardId, newFile, user.getUserId());
//
//        // then
//        List<NoticeDetailResponseDTO> notices = noticeOneResponseDTO.getNoticeDetails();
//
//        System.out.println("========================");
//        notices.forEach(System.out::println);
//    }
//
//    @Test
//    @DisplayName("사용자가 2번째 공지사항에 파일을 등록하면 오류가 발생해야 한다.")
//    void createFileErrorTest() {
//
//        // given
//        UserEntity user = userRepository.findById("402880e7862e5d3201862e5d3ae30002").get();   // 이오이 user_id (MEMBER)
//        String filePath = "error.md";
//
//        Long boardId = noticeService.retrieve().getNotices().get(1).getBoardId();        // 2번째 공지사항 boardId
//
//        NoticeCreateFileRequestDTO newFileDTO = NoticeCreateFileRequestDTO.builder()
//                .boardFilePath(filePath)
//                .build();
//
//        // then
//        assertThrows(RuntimeException.class, () -> {
//            // when
//            noticeService.createFile(boardId, newFileDTO, user.getUserId());
//        });
//    }
//
//    @Test
//    @DisplayName("2번째 공지사항을 등록한 사용자가, 첨부된 두번째 파일의 경로를 'update2.md' 로 수정 해야한다")
//    void updateFileTest() {
//
//        // given
//        UserEntity user = userRepository.findById("402880e7863388c301863388cce40000").get();   // 2번째 공지사항 등록한 user_id (ADMIN)
//
//        Long boardId = noticeService.retrieve().getNotices().get(1).getBoardId();        // 2번째 공지사항 boardId
//
//        int FileIndex = 1;
//        BoardFileEntity fileEntity = noticeService.retrieveOne(boardId).getBoardFileEntityList().get(FileIndex);        // 2번째 공지사항의 두번째 첨부파일 boardFileId
//        String updatePath = "update2.md";
//        NoticeUpdateFileRequestDTO updateFileDTO = NoticeUpdateFileRequestDTO.builder()
//                .boardFilePath(updatePath)
//                .build();
//
//        // when
//        NoticeOneResponseDTO noticeOneResponseDTO = noticeService.updateFile(boardId, fileEntity.getBoardFileId(), updateFileDTO, user.getUserId());
//
//        // then
//        assertEquals(noticeOneResponseDTO.getBoardFileEntityList().get(FileIndex).getBoardFilePath(), updatePath);
//    }
//
//    @Test
//    @DisplayName("2번째 공지사항을 등록하지 않은 관리자가, 첨부된 두번째 파일의 경로를 'update2.md' 로 수정하면 에러가 발생한다.")
//    void updateFileErrorTest() {
//        // given
//        UserEntity user = userRepository.findById("402880e7862e5d3201862e5d3ade0001").get();   // 2번째 공지사항을 등록하지 않은 user_id (ADMIN)
//
//        Long boardId = noticeService.retrieve().getNotices().get(1).getBoardId();        // 2번째 공지사항 boardId
//
//        int FileIndex = 1;
//        BoardFileEntity fileEntity = noticeService.retrieveOne(boardId).getBoardFileEntityList().get(FileIndex);        // 2번째 공지사항의 두번째 첨부파일 boardFileId
//        String updatePath = "update2.md";
//        NoticeUpdateFileRequestDTO updateFileDTO = NoticeUpdateFileRequestDTO.builder()
//                .boardFilePath(updatePath)
//                .build();
//
//        // then
//        assertThrows(RuntimeException.class, () -> {
//            // when
//            noticeService.updateFile(boardId, fileEntity.getBoardFileId(), updateFileDTO, user.getUserId());
//        });
//    }
//
//    @Test
//    @DisplayName("일반 사용자가, 첨부된 두번째 파일의 경로를 'update2.md' 로 수정하면 에러가 발생한다.")
//    void updateFileErrorTest2() {
//        // given
//        UserEntity user = userRepository.findById("402880e7862e5d3201862e5d3ae30002").get();   // 이오이 user_id (MEMBER)
//
//        Long boardId = noticeService.retrieve().getNotices().get(1).getBoardId();        // 2번째 공지사항 boardId
//
//        int FileIndex = 1;
//        BoardFileEntity fileEntity = noticeService.retrieveOne(boardId).getBoardFileEntityList().get(FileIndex);        // 2번째 공지사항의 두번째 첨부파일 boardFileId
//        String updatePath = "update2.md";
//        NoticeUpdateFileRequestDTO updateFileDTO = NoticeUpdateFileRequestDTO.builder()
//                .boardFilePath(updatePath)
//                .build();
//
//        // then
//        assertThrows(RuntimeException.class, () -> {
//            // when
//            noticeService.updateFile(boardId, fileEntity.getBoardFileId(), updateFileDTO, user.getUserId());
//        });
//    }
//
//    @Test
//    @DisplayName("2번째 공지사항의 2번째 파일을 삭제하면 해당 공지사항에 1개의 파일만 남는다.")
//    void deleteFileTest() {
//
//        // given
//        UserEntity user = userRepository.findById("402880e7863388c301863388cce40000").get();   // 2번째 공지사항 등록한 user_id (ADMIN)
//        Long boardId = noticeService.retrieve().getNotices().get(1).getBoardId();        // 2번째 공지사항 boardId
//
//        int FileIndex = 1;
//        BoardFileEntity fileEntity = noticeService.retrieveOne(boardId).getBoardFileEntityList().get(FileIndex);        // 2번째 공지사항의 두번째 첨부파일 boardFileId
//
//        // when
//        NoticeOneResponseDTO noticeOneResponseDTO = noticeService.deleteFile(boardId, fileEntity.getBoardFileId(), user.getUserId());
//
//        // then
//        assertEquals(2, noticeOneResponseDTO.getBoardFileEntityList().size());
//    }
//
//    @Test
//    @DisplayName("2번째 공지사항을 등록하지 않은 관리자가, 첨부된 두번째 파일을 삭제하면 에러가 발생한다.")
//    void deleteFileErrorTest() {
//
//        // given
//        UserEntity user = userRepository.findById("402880e7862e5d3201862e5d3ade0001").get();   // 2번째 공지사항 등록하지 않은 user_id (ADMIN)
//        Long boardId = noticeService.retrieve().getNotices().get(1).getBoardId();        // 2번째 공지사항 boardId
//
//        int FileIndex = 1;
//        BoardFileEntity fileEntity = noticeService.retrieveOne(boardId).getBoardFileEntityList().get(FileIndex);        // 2번째 공지사항의 두번째 첨부파일 boardFileId
//
//        // then
//        assertThrows(RuntimeException.class, () -> {
//            // when
//            noticeService.deleteFile(boardId, fileEntity.getBoardFileId(), user.getUserId());
//        });
//    }
//
//    @Test
//    @DisplayName("일반 사용자가, 첨부된 두번째 파일을 삭제하면 에러가 발생한다.")
//    void deleteFileErrorTest2() {
//
//        // given
//        UserEntity user = userRepository.findById("402880e7862e5d3201862e5d3ae30002").get();   // 이오이 user_id (MEMBER)
//        Long boardId = noticeService.retrieve().getNotices().get(1).getBoardId();        // 2번째 공지사항 boardId
//
//        int FileIndex = 1;
//        BoardFileEntity fileEntity = noticeService.retrieveOne(boardId).getBoardFileEntityList().get(FileIndex);        // 2번째 공지사항의 두번째 첨부파일 boardFileId
//
//        // then
//        assertThrows(RuntimeException.class, () -> {
//            // when
//            noticeService.deleteFile(boardId, fileEntity.getBoardFileId(), user.getUserId());
//        });
//    }
//}