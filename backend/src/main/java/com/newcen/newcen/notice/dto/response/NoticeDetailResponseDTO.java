package com.newcen.newcen.notice.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.newcen.newcen.common.entity.BoardCommentIs;
import com.newcen.newcen.common.entity.BoardEntity;
import com.newcen.newcen.common.entity.BoardType;
import lombok.*;

import java.time.LocalDateTime;

@Setter @Getter @ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class NoticeDetailResponseDTO {      // 공지사항 목록에 보여지는 1개 DTO

    private Long boardId;
    private BoardType boardType;
    private String boardTitle;
    private String boardContent;
    private String boardWriter;

    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime createDate;

    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime boardUpdateDate;

    private BoardCommentIs boardCommentIs;

    private String userId;

    // entity 를 받아서 DTO 로 만들어주는 생성자
    public NoticeDetailResponseDTO(BoardEntity entity) {
        this.boardId = entity.getBoardId();
        this.boardType = entity.getBoardType();
        this.boardTitle = entity.getBoardTitle();
        this.boardContent = entity.getBoardContent();
        this.boardWriter = entity.getBoardWriter();
        this.createDate = entity.getCreateDate();
        this.boardUpdateDate = entity.getBoardUpdateDate();
        this.boardCommentIs = entity.getBoardCommentIs();
        this.userId = entity.getUserId();
    }
}
