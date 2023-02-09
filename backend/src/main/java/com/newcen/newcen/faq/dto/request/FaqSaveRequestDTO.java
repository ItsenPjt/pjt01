package com.newcen.newcen.faq.dto.request;

import com.newcen.newcen.common.entity.BoardCommentIs;
import com.newcen.newcen.common.entity.BoardEntity;
import com.newcen.newcen.common.entity.BoardType;
import com.newcen.newcen.common.entity.UserEntity;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
public class FaqSaveRequestDTO {

    @NotBlank
    private String boardTitle;
    @NotBlank
    private String boardContent;

    public BoardEntity toEntity(UserEntity user) {
        return BoardEntity.builder()
                .boardType(BoardType.FAQ)
                .boardTitle(this.boardTitle)
                .boardContent(this.boardContent)
                .boardWriter(user.getUserName())
                .boardCommentIs(BoardCommentIs.OFF)
                .userId(user.getUserId())
                .build();
    }



}
