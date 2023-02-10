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
public class FaqResponseDTO {

    private Long boardId;
    private String boardTitle;
    private String boardWriter;

    public FaqResponseDTO (BoardEntity entity) {
        this.boardId = entity.getBoardId();
        this.boardTitle = entity.getBoardTitle();
        this.boardWriter = entity.getBoardWriter();
    }





}
