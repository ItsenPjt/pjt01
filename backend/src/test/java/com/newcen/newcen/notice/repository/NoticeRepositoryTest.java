package com.newcen.newcen.notice.repository;

import com.newcen.newcen.common.entity.*;
import com.newcen.newcen.users.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class NoticeRepositoryTest {
    @Autowired
    NoticeRepository noticeRepository;

    @Autowired
    UserRepository userRepository;

    @BeforeEach
    void insertTest() {

        UserEntity user1 = UserEntity
                .builder()
                .userEmail("test@naver.com")
                .userPassword("test12")
                .userName("관리자")
                .userRole(UserRole.ADMIN)
                .build();

        BoardEntity board1 = BoardEntity
                .builder()
                .boardType(BoardType.NOTICE)
                .boardTitle("공지사항1")
                .boardContent("내용1")
                .boardWriter(user1.getUserName())
                .boardCommentIs(BoardCommentIs.OFF)
                .build();

        BoardEntity board2 = BoardEntity
                .builder()
                .boardType(BoardType.NOTICE)
                .boardTitle("공지사항2")
                .boardContent("내용2")
                .boardWriter(user1.getUserName())
                .boardCommentIs(BoardCommentIs.ON)
                .build();

        userRepository.save(user1);
        noticeRepository.save(board1);
        noticeRepository.save(board2);
    }

    @Test
    @DisplayName("공지사항 목록을 조회하면 리스트의 사이즈가 2이어야 한다.")
    void findAllTest() {
        // given

        // when
        List<BoardEntity> list = noticeRepository.findAll();

        // then
        assertEquals(2, list.size());
        list.forEach(System.out::println);
    }
}