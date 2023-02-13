package com.newcen.newcen.question.repository;

import com.newcen.newcen.common.entity.BoardFileEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class QuestionsFileRepositoryTest {
    @Autowired
    QuestionsFileRepository questionsFileRepository;
    @Test
    void saveTest() {
        BoardFileEntity boardFile = BoardFileEntity.builder()
                .boardFilePath("ㅈㅈㅈ")
                .boardId(1L)
                .build();

        BoardFileEntity save = questionsFileRepository.save(boardFile);

    }

}