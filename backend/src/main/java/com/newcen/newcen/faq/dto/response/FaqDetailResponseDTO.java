package com.newcen.newcen.faq.dto.response;

import com.newcen.newcen.common.entity.BoardEntity;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
public class FaqDetailResponseDTO {

    private Long boardId;
    private String boardTitle;
    private String boardContent;
    private String boardWriter;
    private LocalDateTime boardCreatedate;
    private LocalDateTime boardUpdatedate;

    public FaqDetailResponseDTO(BoardEntity entity) {
        this.boardId = entity.getBoardId();
        this.boardTitle = entity.getBoardTitle();
        this.boardContent = entity.getBoardContent();
        this.boardWriter = entity.getBoardWriter();
        this.boardCreatedate = entity.getCreateDate();
        this.boardUpdatedate = entity.getBoardUpdateDate();
    }


}
