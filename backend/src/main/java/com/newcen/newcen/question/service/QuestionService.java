package com.newcen.newcen.question.service;

import com.newcen.newcen.common.config.security.TokenProvider;
import com.newcen.newcen.common.entity.BoardEntity;
import com.newcen.newcen.common.entity.UserEntity;
import com.newcen.newcen.question.repository.QuestionsRepository;
import com.newcen.newcen.question.request.QuestionCreateRequestDTO;
import com.newcen.newcen.question.response.QuestionListResponseDTO;
import com.newcen.newcen.question.response.QuestionResponseDTO;
import com.newcen.newcen.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class QuestionService {
    private final UserRepository userRepository;
    private final QuestionsRepository questionsRepository;

    private final TokenProvider tokenProvider;


    //문의사항 목록조회
    public QuestionListResponseDTO retrieve(){
        List<BoardEntity> entityList = questionsRepository.findAll();
        List<QuestionResponseDTO> responseDTO = entityList.stream()
                .map(QuestionResponseDTO::new)
                .collect(Collectors.toList());
        return QuestionListResponseDTO.builder()
                .data(responseDTO)
                .build();
    }

    //문의사항 등록
    public QuestionResponseDTO create(final QuestionCreateRequestDTO dto, String userId){
        UserEntity user = null;
        user = userRepository.findById(userId).get();
        if (user == null){
            throw new RuntimeException("해당 유저가 없습니다.");
        }

        BoardEntity board = dto.toEntity(user);
        BoardEntity createdBoard = questionsRepository.save(board);
        QuestionResponseDTO createdQuestion = new QuestionResponseDTO(createdBoard);

        return createdQuestion;



    }

    public QuestionResponseDTO update(){



        return null;
    }

}
