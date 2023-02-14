package com.newcen.newcen.commentFile.dto.request;

import com.newcen.newcen.common.entity.CommentEntity;
import com.newcen.newcen.common.entity.CommentFileEntity;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentFileCreateRequest {
    @NotBlank
    private String commentFilePath;

    public CommentFileEntity toEntity(CommentEntity comment){
        return CommentFileEntity.builder()
                .commentId(comment.getCommentId())
                .commentFilePath(this.commentFilePath)
                .userEmail(comment.getUserEmail())
                .build();
    }
}
