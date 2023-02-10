package com.newcen.newcen.comment.service;

import com.newcen.newcen.comment.dto.request.CommentCreateRequest;
import com.newcen.newcen.comment.dto.response.CommentListResponseDTO;
import com.newcen.newcen.common.entity.BoardEntity;
import com.newcen.newcen.common.entity.UserEntity;
import com.newcen.newcen.question.repository.QuestionsRepository;
import com.newcen.newcen.question.repository.QuestionsRepositorySupport;
import com.newcen.newcen.question.request.QuestionCreateRequestDTO;
import com.newcen.newcen.question.response.QuestionResponseDTO;
import com.newcen.newcen.users.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class CommentServiceTest {
    @Autowired
    CommentService commentService;

    @Autowired
    QuestionsRepository questionsRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    QuestionsRepositorySupport questionsRepositorySupport;


    @Test
    @DisplayName("댓글을 생성해야 한다.")
    void create(){
        UserEntity user = userRepository.findById("402880c38633b44d018633b4584f0000").get();
        BoardEntity board = questionsRepository.findById(11L).get();
        CommentCreateRequest dto = CommentCreateRequest.builder()
                .commentContent("하하")
                .build();
        CommentListResponseDTO comment = commentService.createComment(dto, user.getUserId(), board.getBoardId());

        assertEquals("암호맨",comment.getData().get(1).getCommentWriter());
    }

    @Test
    @DisplayName("댓글을 삭제해야한다.")
    void delete(){
        UserEntity user = userRepository.findById("402880c38633b44d018633b4584f0000").get();
        BoardEntity board = questionsRepository.findById(11L).get();
        commentService.deleteComment(user.getUserId(),13L);

    }


}