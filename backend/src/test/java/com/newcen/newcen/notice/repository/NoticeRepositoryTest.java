package com.newcen.newcen.notice.repository;

import com.newcen.newcen.common.entity.*;
import com.newcen.newcen.users.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class NoticeRepositoryTest {
    @Autowired
    NoticeRepository noticeRepository;

    @Autowired
    UserRepository userRepository;

//    @BeforeEach
//    void insertTest() {
//
//        UserEntity user1 = UserEntity.builder()
//                .userEmail("test@naver.com")
//                .userPassword("test12")
//                .userName("관리자")
//                .userRole(UserRole.ADMIN)
//                .build();
//
//        userRepository.save(user1);
//
//        BoardEntity board1 = BoardEntity.builder()
//                .boardId(1L)
//                .boardType(BoardType.NOTICE)
//                .boardTitle("공지사항1")
//                .boardContent("내용1")
//                .userId(user1.getUserId())
//                .boardWriter(user1.getUserName())
//                .boardCommentIs(BoardCommentIs.OFF)
//                .build();
//
//        BoardEntity board2 = BoardEntity.builder()
//                .boardId(2L)
//                .boardType(BoardType.NOTICE)
//                .boardTitle("공지사항2")
//                .boardContent("내용2")
//                .userId(user1.getUserId())
//                .boardWriter(user1.getUserName())
//                .boardCommentIs(BoardCommentIs.ON)
//                .build();
//
//        noticeRepository.save(board1);
//        noticeRepository.save(board2);
//    }

    @Test
    @DisplayName("공지사항 목록을 조회하면 리스트의 사이즈가 4이어야 한다.")
    @Transactional
    void findAllTest() {
        // given

        // when
        List<BoardEntity> list = noticeRepository.findAll();

        // then
        assertEquals(4, list.size());
        list.forEach(System.out::println);
    }

    @Test
    @DisplayName("관리자의 공지사항을 등록해야 한다.")
    void saveNoticeWithUserTest() {
        UserEntity user = userRepository.findByUserEmail("test@naver.com");

        // given
        BoardEntity board = BoardEntity.builder()
                .boardType(BoardType.NOTICE)
                .boardTitle("공지사항 추가 테스트2")
                .boardContent("공지사항 내용2")
                .boardCommentIs(BoardCommentIs.ON)
                .userId(user.getUserId())
                .boardWriter(user.getUserName())
                .user(user)
                .build();

        // when
        BoardEntity saveBoard = noticeRepository.save(board);

        // then
        assertEquals(saveBoard.getUser().getUserName(), user.getUserName());
    }
}