package com.newcen.newcen.question.service;

import com.newcen.newcen.common.entity.BoardType;
import com.newcen.newcen.common.entity.UserEntity;
import com.newcen.newcen.question.repository.QuestionsRepository;
import com.newcen.newcen.question.request.QuestionCreateRequestDTO;
import com.newcen.newcen.question.response.QuestionListResponseDTO;
import com.newcen.newcen.question.response.QuestionResponseDTO;
import com.newcen.newcen.users.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class QuestionServiceTest {

    @Autowired
    QuestionService questionService;

    @Autowired
    private QuestionsRepository questionsRepository;
    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("문의사항 목록을 조회해야 한다.")
    @Transactional
    void getList(){
        QuestionListResponseDTO questionResponseDTOS = questionService.retrieve();
        questionResponseDTOS.getData().stream()
                .forEach(t-> System.out.println(t));

        assertEquals(2,questionResponseDTOS.getData().size());

    }

    @Test
    @DisplayName("문의사항 목록을 생성한다.")
    void create(){
        UserEntity user = userRepository.findById("402880c3862a5a4e01862a5a5a4c0000").get();
        QuestionCreateRequestDTO newQuestion = QuestionCreateRequestDTO
                .builder()
                .boardContent("안녕하세요")
                .boardTitle("하이하이")
                .boardWriter(user.getUserName())
                .boardType(BoardType.QUESTION)
                .build();

        QuestionResponseDTO res = questionService.create(newQuestion, user.getUserId());

        assertEquals("김진행1",res.getBoardWriter());


    }

}