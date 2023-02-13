package com.newcen.newcen.commentFile.dto.response;

import com.newcen.newcen.common.entity.CommentFileEntity;
import lombok.*;

import java.util.List;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentFileResponseDTO {

    private Long commentId;
    private String commentFileId;
    private String commentFilePath;

    public CommentFileResponseDTO(CommentFileEntity commentFileEntity){
        this.commentFileId = commentFileEntity.getCommentFileId();
        this.commentId = commentFileEntity.getCommentId();
        this.commentFilePath = commentFileEntity.getCommentFilePath();
    }


}
