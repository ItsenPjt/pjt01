package com.newcen.newcen.notice.dto.request;

import com.newcen.newcen.common.entity.BoardCommentIs;
import com.newcen.newcen.common.entity.BoardEntity;
import lombok.*;

@Setter @Getter @ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class NoticeCreateRequestDTO {

    private String boardTitle;
    private String boardContent;
    private BoardCommentIs boardCommentIs;

    // DTO 를 entity 로 변환
    public BoardEntity toEntity() {
        return BoardEntity
                .builder()
                .boardTitle(this.boardTitle)
                .boardContent(this.boardContent)
                .boardCommentIs(this.boardCommentIs)
                .build();
    }
}
