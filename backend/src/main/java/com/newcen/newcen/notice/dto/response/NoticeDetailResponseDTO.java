package com.newcen.newcen.notice.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.newcen.newcen.common.entity.BoardEntity;
import lombok.*;

import java.time.LocalDateTime;

@Setter @Getter @ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class NoticeDetailResponseDTO {      // 공지사항 1개 DTO

    private Long boardId;
    private String boardTitle;
    private String boardWriter;
    @JsonFormat(pattern = "yyyy년 MM월 dd일")
    private LocalDateTime regDate;

    // entity 를 받아서 DTO 로 만들어주는 생성자
    public NoticeDetailResponseDTO(BoardEntity entity) {
        this.boardId = entity.getBoardId();
        this.boardTitle = entity.getBoardTitle();
        this.boardWriter = entity.getBoardWriter();
        this.regDate = entity.getCreateDate();
    }
}
