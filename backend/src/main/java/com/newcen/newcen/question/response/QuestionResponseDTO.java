package com.newcen.newcen.question.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.newcen.newcen.common.entity.BoardCommentIs;
import com.newcen.newcen.common.entity.BoardEntity;
import com.newcen.newcen.common.entity.BoardType;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestionResponseDTO {

    private BoardType boardType;

    private String boardTitle;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createDate;

    private String boardContent;

    private LocalDateTime boardUpdateDate;

    private BoardCommentIs boardCommentIs;

    private String userName;
    public QuestionResponseDTO(BoardEntity boardEntity){
        this.boardCommentIs= boardEntity.getBoardCommentIs();
        this.createDate=boardEntity.getCreateDate();
        this.boardContent=boardEntity.getBoardContent();
        this.boardTitle=boardEntity.getBoardTitle();
        this.boardUpdateDate=boardEntity.getBoardUpdateDate();
        this.userName=boardEntity.getUser().getUserName();
    }
}
