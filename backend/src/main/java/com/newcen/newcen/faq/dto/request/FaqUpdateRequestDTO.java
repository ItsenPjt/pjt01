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
public class FaqUpdateRequestDTO {


    private Long boardId;
    @NotBlank
    private String boardTitle;
    @NotBlank
    private String boardContent;

    public void setBoardId(Long boardId) {
        this.boardId = boardId;
    }


}
