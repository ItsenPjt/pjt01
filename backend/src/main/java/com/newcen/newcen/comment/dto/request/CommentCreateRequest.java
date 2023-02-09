package com.newcen.newcen.comment.dto.request;

import com.newcen.newcen.common.entity.BoardEntity;
import com.newcen.newcen.common.entity.CommentEntity;
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

    private String comment_writer;

    public CommentEntity toEntity(BoardEntity board){
        return CommentEntity.builder()
                .board(board)
                .boardId(board.getBoardId())
                .commentContent(this.commentContent)
                .commentWriter(this.comment_writer)
                .build();
    }
}
