package com.newcen.newcen.comment.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.newcen.newcen.common.entity.CommentEntity;
import com.newcen.newcen.common.entity.CommentFileEntity;
import com.newcen.newcen.common.entity.CommentReplyEntity;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentResponseDTO {

    private Long commentId;

    private Long boardId;

    private String commentContent;

    private String commentWriter;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime CommentCreateDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime CommentUpdateDate;

    private List<CommentFileEntity> commentFileList;

    private List<CommentReplyEntity> commentReplyList;

    public CommentResponseDTO(CommentEntity comment){
        this.commentId = comment.getCommentId();
        this.boardId =comment.getBoardId();
        this.commentContent = comment.getCommentContent();
        this.commentWriter = comment.getCommentWriter();
        this.CommentCreateDate = comment.getCommentCreateDate();
        this.CommentUpdateDate =comment.getCommentUpdateDate();
        this.commentFileList = comment.getCommentFileList();
        this.commentReplyList =comment.getCommentReplyList();
    }

}
