package com.newcen.newcen.question.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.newcen.newcen.common.entity.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestionsOneResponseDTO {

    private Long boardId;
    private BoardType boardType;

    private String boardTitle;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createDate;

    private String boardContent;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime boardUpdateDate;

    private BoardCommentIs boardCommentIs;

    private String boardWriter;

    private List<BoardFileEntity> boardFileList;
    private List<CommentEntity> commentList;

    private String userId;

    public QuestionsOneResponseDTO(BoardEntity boardEntity){
        this.boardId = boardEntity.getBoardId();
        this.boardCommentIs= boardEntity.getBoardCommentIs();
        this.createDate=boardEntity.getCreateDate();
        this.boardContent=boardEntity.getBoardContent();
        this.boardTitle=boardEntity.getBoardTitle();
        this.boardUpdateDate=boardEntity.getBoardUpdateDate();
        this.boardWriter=boardEntity.getUser().getUserName();
        this.boardFileList=boardEntity.getBoardFileEntityList();
        this.commentList=boardEntity.getCommentEntityList();
        this.boardType = boardEntity.getBoardType();
        this.userId = boardEntity.getUserId();
    }
}
