package com.newcen.newcen.question.service;

import com.newcen.newcen.question.repository.QuestionsRepository;
import com.newcen.newcen.question.response.QuestionListResponseDTO;
import com.newcen.newcen.question.response.QuestionResponseDTO;
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

    @Test
    @DisplayName("문의사항 목록을 조회해야 한다.")
    @Transactional
    void getList(){
        QuestionListResponseDTO questionResponseDTOS = questionService.retrieve();
        questionResponseDTOS.getData().stream()
                .forEach(t-> System.out.println(t));

        assertEquals(2,questionResponseDTOS.getData().size());

    }

}