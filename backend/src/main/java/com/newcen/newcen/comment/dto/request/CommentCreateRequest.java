package com.newcen.newcen.comment.dto.request;

import com.newcen.newcen.common.entity.BoardEntity;
import com.newcen.newcen.common.entity.CommentEntity;
import com.newcen.newcen.common.entity.UserEntity;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentCreateRequest {


    private String commentContent;

    public CommentEntity toEntity(BoardEntity board, UserEntity user){
        return CommentEntity.builder()
                .boardId(board.getBoardId())
                .userId(user.getUserId())
                .commentContent(this.commentContent)
                .commentWriter(user.getUserName())

                .build();
    }
}
