package com.newcen.newcen.notice.dto.request;

import com.newcen.newcen.common.entity.BoardCommentIs;
import com.newcen.newcen.common.entity.BoardEntity;
import com.newcen.newcen.common.entity.BoardType;
import com.newcen.newcen.common.entity.UserEntity;
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
    public BoardEntity toEntity(UserEntity user) {
        return BoardEntity.builder()
                .boardTitle(this.boardTitle)
                .boardContent(this.boardContent)
                .boardCommentIs(this.boardCommentIs)
                .boardType(BoardType.NOTICE)
                .user(user)
                .userId(user.getUserId())
                .boardWriter(user.getUserName())
                .build();
    }
}
