package com.newcen.newcen.question.request;

import com.newcen.newcen.common.entity.BoardCommentIs;
import com.newcen.newcen.common.entity.BoardEntity;
import com.newcen.newcen.common.entity.BoardType;
import com.newcen.newcen.common.entity.UserEntity;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestionCreateRequestDTO {


    @NotBlank
    private String boardTitle;
    @NotBlank
    private String boardContent;

    private BoardCommentIs boardCommentIs;



    public BoardEntity toEntity(UserEntity user){
        return BoardEntity.builder()
                .boardWriter(user.getUserName())
                .boardTitle(this.boardTitle)
                .boardContent(this.boardContent)
                .boardCommentIs(this.boardCommentIs)
                .boardType(BoardType.QUESTION)
                .user(user)
                .userId(user.getUserId())
                .build();
    }





}
