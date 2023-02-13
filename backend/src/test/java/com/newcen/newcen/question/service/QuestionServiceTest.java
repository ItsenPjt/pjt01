package com.newcen.newcen.question.service;

import com.newcen.newcen.common.entity.BoardEntity;
import com.newcen.newcen.common.entity.BoardFileEntity;
import com.newcen.newcen.common.entity.BoardType;
import com.newcen.newcen.common.entity.UserEntity;
import com.newcen.newcen.question.repository.QuestionsFileRepository;
import com.newcen.newcen.question.repository.QuestionsRepository;
import com.newcen.newcen.question.repository.QuestionsRepositorySupport;
import com.newcen.newcen.question.request.QuestionCreateRequestDTO;
import com.newcen.newcen.question.request.QuestionUpdateRequestDTO;
import com.newcen.newcen.question.response.QuestionListResponseDTO;
import com.newcen.newcen.question.response.QuestionResponseDTO;
import com.newcen.newcen.question.response.QuestionsOneResponseDTO;
import com.newcen.newcen.users.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class QuestionServiceTest {

    @Autowired
    QuestionService questionService;
    @Autowired
    QuestionsRepository questionsRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    QuestionsRepositorySupport questionsRepositorySupport;
    @Autowired
    private QuestionsFileRepository questionsFileRepository;

    @Test
    @DisplayName("문의사항 목록을 조회해야 한다.")
    @Transactional
    void getList(){
        QuestionListResponseDTO questionResponseDTOS = questionService.retrieve();
        questionResponseDTOS.getData().stream()
                .forEach(t-> System.out.println(t));

        assertEquals(4,questionResponseDTOS.getData().size());

    }

    @Test
    @DisplayName("문의사항을 생성한다.")
    void create(){
        UserEntity user = userRepository.findById("402880c38633b44d018633b4584f0000").get();
        QuestionCreateRequestDTO newQuestion = QuestionCreateRequestDTO
                .builder()
                .boardContent("안녕하세요")
                .boardTitle("하이하이")
                .build();

        QuestionResponseDTO res = questionService.create(newQuestion, user.getUserId());

        assertEquals("김진행1",res.getBoardWriter());
    }
    @Test
    @DisplayName("문의사항 상세를 조회한다.")
    @Transactional(readOnly = true)
    void viewDetail(){
        Long board= 1L;
        QuestionsOneResponseDTO questionResponseDTO = questionService.questionDetail(1L);
        assertEquals("김진행1",questionResponseDTO.getBoardWriter());
    }

    @Test
    @DisplayName("문의사항을 수정한다.")
    @Transactional(readOnly = true)
    void update(){
        Long board = 1L;
        QuestionUpdateRequestDTO qes = QuestionUpdateRequestDTO.builder()
                .boardTitle("안녕")
                .boardContent("김진행입니다.")
                .build();

        String userId = "402880c3862a5ba301862a5badf20000";
        QuestionResponseDTO questionResponseDTO = questionService.updateQuestion(qes,userId,board);
        assertEquals("김진행입니다.",questionResponseDTO.getBoardContent());
    }

    @Test
    @DisplayName("문의사항을 삭제한다.")
    @Transactional(readOnly = true)
    void delte(){
        Long boardId = 9L;
        String userId = "402880c3862a5ba301862a5badf20000";
        Boolean std = questionService.deleteQuestion(userId,boardId);
        assertEquals(true,std);
    }
    @Test
    @Transactional
    @DisplayName("문의사항 파일을 등록한다.")
    @Rollback(value = false)
    void save(){
        BoardFileEntity boardFile = BoardFileEntity.builder()
                .boardId(12L)
                .boardFilePath("www")
                .build();
        String userId = "402880c3862a5ba301862a5badf20000";
        Long boardId = 12L;
        String boardPath = "www";
        BoardEntity board = questionsRepositorySupport.findBoardByUserIdAndBoardId(userId,boardId);
        board.getBoardFileEntityList().add(boardFile);
        BoardEntity save1 = questionsRepository.save(board);
        System.out.println(save1.getBoardFileEntityList());

    }
    @Test
    @DisplayName("문의사항 파일을 삭제한다.")
    void deleteFile(){
        String id = "402880c38633ea64018633f00b5a0001";

        questionsFileRepository.deleteById(id);



    }
}