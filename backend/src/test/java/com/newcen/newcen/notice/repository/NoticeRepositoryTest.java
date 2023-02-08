package com.newcen.newcen.notice.repository;

import com.newcen.newcen.common.entity.*;
import com.newcen.newcen.users.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
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

    /*
    @BeforeEach
    void insertTest() {

        UserEntity user1 = UserEntity.builder()
                .userEmail("test@naver.com")
                .userPassword("test")
                .userName("인사팀")
                .userRole(UserRole.ADMIN)
                .build();

        UserEntity user2 = UserEntity.builder()
                .userEmail("test2@naver.com")
                .userPassword("test2")
                .userName("경영팀")
                .userRole(UserRole.ADMIN)
                .build();

        UserEntity user3 = UserEntity.builder()
                .userEmail("test3@naver.com")
                .userPassword("test3")
                .userName("이오이")
                .userRole(UserRole.MEMBER)
                .build();

        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);

        BoardEntity board1 = BoardEntity.builder()
                .boardId(1L)
                .boardType(BoardType.NOTICE)
                .boardTitle("공지사항1")
                .boardContent("내용1")
                .userId(user1.getUserId())
                .boardWriter(user1.getUserName())
                .boardCommentIs(BoardCommentIs.OFF)
                .build();

        BoardEntity board2 = BoardEntity.builder()
                .boardId(2L)
                .boardType(BoardType.NOTICE)
                .boardTitle("공지사항2")
                .boardContent("내용2")
                .userId(user2.getUserId())
                .boardWriter(user2.getUserName())
                .boardCommentIs(BoardCommentIs.ON)
                .build();

        noticeRepository.save(board1);
        noticeRepository.save(board2);
    }
    */

    @Test
    @DisplayName("공지사항 목록을 조회하면 리스트의 사이즈가 2이어야 한다.")
    @Transactional
    void findAllTest() {
        // given

        // when
        List<BoardEntity> list = noticeRepository.findAll();

        // then
        assertEquals(2, list.size());
        list.forEach(System.out::println);
    }

    @Test
    @DisplayName("관리자의 공지사항을 등록해야 한다.")
    void saveNoticeWithUserTest() {
        UserEntity user = userRepository.findByUserEmail("test2@naver.com");

        // given
        BoardEntity board = BoardEntity.builder()
                .boardType(BoardType.NOTICE)
                .boardTitle("공지사항 추가 테스트")
                .boardContent("공지사항 내용")
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