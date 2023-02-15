package com.newcen.newcen.question.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.newcen.newcen.common.config.security.TokenProvider;
import com.newcen.newcen.common.dto.request.SearchCondition;
import com.newcen.newcen.common.entity.BoardEntity;
import com.newcen.newcen.common.entity.BoardFileEntity;
import com.newcen.newcen.common.entity.UserEntity;
import com.newcen.newcen.common.repository.BoardFileRepository;
import com.newcen.newcen.question.repository.QuestionsFileRepository;
import com.newcen.newcen.question.repository.QuestionsRepository;
import com.newcen.newcen.question.repository.QuestionsRepositorySupport;
import com.newcen.newcen.question.request.QuestionCreateRequestDTO;
import com.newcen.newcen.question.request.QuestionUpdateRequestDTO;
import com.newcen.newcen.question.response.QuestionListResponseDTO;
import com.newcen.newcen.question.response.QuestionResponseDTO;
import com.newcen.newcen.question.response.QuestionsOneResponseDTO;
import com.newcen.newcen.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class QuestionService {
    private final UserRepository userRepository;
    private final QuestionsRepository questionsRepository;

    private final QuestionsFileRepository questionsFileRepository;
    private final BoardFileRepository boardFileRepository;

    private final QuestionsRepositorySupport questionsRepositorySupport;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final AmazonS3 amazonS3;


    //문의사항 목록조회
    public QuestionListResponseDTO retrieve(){
        List<BoardEntity> entityList = questionsRepositorySupport.getQuestionList();
        List<QuestionResponseDTO> responseDTO = entityList.stream()
                .map(QuestionResponseDTO::new)
                .collect(Collectors.toList());
        return QuestionListResponseDTO.builder()
                .data(responseDTO)
                .build();
    }
    //문의사항 목록조회
    public PageImpl<QuestionResponseDTO> getPageList(Pageable pageable){
        PageImpl<QuestionResponseDTO> result = questionsRepositorySupport.getQuestionListPage(pageable);
        return result;
    }
    //문의사항 검색 및 페이지 제네이션
    public PageImpl<QuestionResponseDTO> getPageListWithSearch(SearchCondition searchCondition, Pageable pageable){
        PageImpl<QuestionResponseDTO> result = questionsRepositorySupport.getQuestionListPageWithSearch(searchCondition, pageable);
        return result;
    }

    //문의사항 상세조회
    public QuestionsOneResponseDTO questionDetail(Long boardId){
        BoardEntity boardGet = questionsRepository.getById(boardId);
//        BoardEntity boardGet = questionsRepository.getById(boardId);
        return new QuestionsOneResponseDTO(boardGet);
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
    //문의사항 수정
    public QuestionResponseDTO updateQuestion(QuestionUpdateRequestDTO dto, String userId, Long boardId){
        UserEntity user = userRepository.getById(userId);
        BoardEntity boardGet = questionsRepositorySupport.findBoardByUserIdAndBoardId(userId,boardId);
        String content =null;
        String title = null;
        if (dto.getBoardContent()==null || dto.getBoardContent()==""){
            content = boardGet.getBoardContent();
        }else {
            content = dto.getBoardContent();
        }
        if (dto.getBoardTitle()==null || dto.getBoardTitle()==""){
            title = boardGet.getBoardContent();
        }else {
            title = dto.getBoardTitle();
        }

        boardGet.updateBoard(title,content);
        BoardEntity savedBoard = questionsRepository.save(boardGet);
        return new QuestionResponseDTO(savedBoard);
    }

    //문의사항 삭제
    public boolean deleteQuestion(String userId, Long boardId){
        BoardEntity boardGet = questionsRepositorySupport.findBoardByUserIdAndBoardId(userId,boardId);
        UserEntity user = userRepository.findById(userId).get();
        if (!Objects.equals(boardGet.getUserId(), user.getUserId())){
            throw new RuntimeException("본인이 작성한 글이 아닙니다.");
        }
        questionsRepository.delete(boardGet);
        return true;
    }

    // 게시물 파일 등록
    public QuestionsOneResponseDTO createFile(String userId, Long boardId, String boardFilePath){
        UserEntity user = null;
        user = userRepository.findById(userId).get();
        BoardEntity board = questionsRepositorySupport.findBoardByUserIdAndBoardId(userId,boardId);
        if (!Objects.equals(board.getUserId(), user.getUserId())){
            throw new RuntimeException("본인이 작성한 글이 아닙니다.");
        }
        if (!board.getUserId().equals(userId)){
            throw new RuntimeException("본인 작성글이 아닙니다.");
        }

        BoardFileEntity boardFileEntity = BoardFileEntity.builder()
                .boardFilePath(boardFilePath)
                .boardId(boardId)
                .build();
        BoardFileEntity savedFileEntity = questionsFileRepository.save(boardFileEntity);
        board.getBoardFileEntityList().add(savedFileEntity);
        BoardEntity savedBoard = questionsRepository.save(board);
        return new QuestionsOneResponseDTO(savedBoard);
    }
    //게시물 파일 수정
    public QuestionsOneResponseDTO updateFile(String userId, Long boardId, String boardFilePath, String boardFileId){
        UserEntity user = null;
        BoardEntity board = questionsRepositorySupport.findBoardByUserIdAndBoardId(userId,boardId);
        user = userRepository.findById(userId).get();
        if (!Objects.equals(board.getUserId(), user.getUserId())){
            throw new RuntimeException("본인이 작성한 글이 아닙니다.");
        }

        BoardFileEntity boardFileGetById = questionsFileRepository.getById(boardFileId);
        boardFileGetById.setBoardFilePath(boardFilePath);
        questionsFileRepository.save(boardFileGetById);

        BoardEntity savedBoard = questionsRepository.save(board);
        return new QuestionsOneResponseDTO(savedBoard);
    }
    //게시물 파일 삭제
    public QuestionResponseDTO deleteFile(String userId, Long boardId, String boardFileId){
        BoardEntity board = questionsRepositorySupport.findBoardByUserIdAndBoardId(userId,boardId);
        UserEntity user = userRepository.findById(userId).get();
        BoardFileEntity boardFile = questionsFileRepository.findById(boardFileId).get();
        if (!Objects.equals(board.getUserId(), user.getUserId())){
            throw new RuntimeException("본인이 작성한 글이 아닙니다.");
        }
        try {
            questionsFileRepository.deleteById(boardFileId);
//            amazonS3.deleteObject(new DeleteObjectRequest(bucket, fileName));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        BoardEntity res = BoardEntity.builder()
                .boardTitle(board.getBoardTitle())
                .boardContent(board.getBoardContent())
                .boardType(board.getBoardType())
                .boardWriter(board.getBoardWriter())
                .boardCommentIs(board.getBoardCommentIs())
                .boardId(board.getBoardId())
                .boardTitle(board.getBoardTitle())
                .boardUpdateDate(board.getBoardUpdateDate())
                .userId(userId)
                .user(user)
                .build();

        return new QuestionResponseDTO(res);
    }


}
