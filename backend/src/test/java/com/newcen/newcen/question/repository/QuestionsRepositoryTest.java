package com.newcen.newcen.question.repository;

import com.newcen.newcen.common.entity.*;
import com.newcen.newcen.users.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;



@SpringBootTest
@Rollback
class QuestionsRepositoryTest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    QuestionsRepository questionsRepository;

    @Test
    @DisplayName("회원은 문의게시글을 작성해야 한다.")
    @Transactional
    void makeQuestion(){
        UserEntity user1 = UserEntity.builder()
                .userName("김진행3")
                .userEmail("dhkd@nave1r.com")
                .userPassword("12341")

                .build();
        UserEntity user2 = userRepository.save(user1);
        BoardEntity board1 = BoardEntity.builder()
                .boardId(2L)
                .boardContent("김")
                .boardCommentIs(BoardCommentIs.ON)
                .boardType(BoardType.QUESTION)
                .boardTitle("김")
                .userId(user2.getUserId())
                .boardWriter(user1.getUserName())
                .build();

        BoardEntity board2 = questionsRepository.save(board1);



        Assertions.assertEquals("김",board2.getBoardTitle());
    }

    @Test
    @DisplayName("문의사항 목록을 조회한다.")
    @Transactional
    void getList(){
        List<BoardEntity> board = questionsRepository.findAll();
        Assertions.assertEquals(3,board.size());
    }

}