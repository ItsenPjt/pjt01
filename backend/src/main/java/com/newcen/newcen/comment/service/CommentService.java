package com.newcen.newcen.comment.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.newcen.newcen.comment.dto.request.CommentCreateRequest;
import com.newcen.newcen.comment.dto.request.CommentUpdateRequest;
import com.newcen.newcen.comment.dto.response.CommentListResponseDTO;
import com.newcen.newcen.comment.dto.response.CommentResponseDTO;
import com.newcen.newcen.comment.repository.CommentRepository;
import com.newcen.newcen.comment.repository.CommentRepositorySupport;
import com.newcen.newcen.commentFile.repository.CommentFileRepositorySupport;
import com.newcen.newcen.common.entity.BoardEntity;
import com.newcen.newcen.common.entity.CommentEntity;
import com.newcen.newcen.common.entity.CommentFileEntity;
import com.newcen.newcen.common.entity.UserEntity;
import com.newcen.newcen.question.repository.QuestionsRepository;
import com.newcen.newcen.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommentService {
    //공용

    private final UserRepository userRepository;


    private final QuestionsRepository questionsRepository;


    private final CommentRepository commentRepository;
    private final CommentRepositorySupport commentRepositorySupport;

    private final CommentFileRepositorySupport commentFileRepositorySupport;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final AmazonS3 amazonS3;
    //댓글 목록 조회
    public CommentListResponseDTO retrive(Long boardId) {
        List<CommentEntity> commentList = commentRepositorySupport.findAllByBoardId(boardId);
        List<CommentResponseDTO> responseDTO = commentList.stream()
                .map(CommentResponseDTO::new)
                .collect(Collectors.toList());
        return CommentListResponseDTO.builder()
                .data(responseDTO)
                .build();

    }

    //댓글 목록 조회 페이지 제네이션

    public PageImpl<CommentResponseDTO> getCommentListPage(Pageable pageable,Long boardId){
        PageImpl<CommentResponseDTO> result = commentRepositorySupport.getCommentListPage(pageable,boardId);
        return result;
    }

    //댓글 생성
    public CommentListResponseDTO createComment(final CommentCreateRequest dto, String userId, Long boardId) {
        UserEntity user = null;
        user = userRepository.findById(userId).get();
        Optional<BoardEntity> board = questionsRepository.findById(boardId);
        if (user == null) {
            throw new RuntimeException("해당 유저가 없습니다.");
        }
        if (board == null) {
            throw new RuntimeException("해당 게시글이 없습니다.");
        }

        CommentEntity commentEntity = dto.toEntity(board.get(), user);
        commentRepository.save(commentEntity);
        return retrive(boardId);
    }
    //댓글 수정
    public CommentListResponseDTO updateComment(final CommentUpdateRequest dto, String userId, Long boardId, Long commentId) {
        CommentEntity getComment = commentRepository.findById(commentId).get();
        if (getComment==null){
            throw new RuntimeException("해당 댓글이 없습니다.");
        }
        UserEntity user = userRepository.findByUserId(userId).get();
        if (!Objects.equals(getComment.getCommentWriter(), user.getUserName())) {
            throw new RuntimeException("본인이 작성한 댓글이 아닙니다.");
        }
        String commentContent = null;
        if (dto.getCommentContent() == null || dto.getCommentContent().equals("") ) {
            commentContent = getComment.getCommentContent();
        } else {
            commentContent = dto.getCommentContent();
        }
        getComment.updateComment(commentContent);
        commentRepository.save(getComment);
        return retrive(boardId);
    }
    //댓글 삭제 caseCade 설정 완료 파일도 동시에 삭제
    public boolean deleteComment(String userId, Long commentId) {
        CommentEntity getComment = commentRepository.findById(commentId).get();
        UserEntity user = userRepository.findById(userId).get();
        if (!Objects.equals(getComment.getCommentWriter(), user.getUserName())) {
            throw new RuntimeException("본인이 작성한 댓글이 아닙니다.");
        }
        List<CommentFileEntity> commentFileList = commentFileRepositorySupport.findCommentFileListByCommentId(commentId);
        if (commentFileList.size() !=0 && commentFileList !=null){
            commentFileList.forEach(t-> amazonS3.deleteObject(new DeleteObjectRequest(bucket, t.getCommentFilePath())));
        }
        commentRepository.delete(getComment);
        return true;
    }
}
