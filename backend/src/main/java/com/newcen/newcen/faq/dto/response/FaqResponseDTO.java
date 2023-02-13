package com.newcen.newcen.faq.dto.response;


import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime boardCreatedate;

    public FaqResponseDTO (BoardEntity entity) {
        this.boardId = entity.getBoardId();
        this.boardTitle = entity.getBoardTitle();
        this.boardWriter = entity.getBoardWriter();
        this.boardCreatedate = entity.getCreateDate();
    }





}
