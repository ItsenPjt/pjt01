package com.newcen.newcen.notice.dto.request;

import com.newcen.newcen.common.entity.BoardEntity;
import com.newcen.newcen.common.entity.BoardFileEntity;
import lombok.*;

@Setter @Getter @ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class NoticeCreateFileRequestDTO {

    private String boardFilePath;

    // DTO 를 entity 로 변환
    public BoardFileEntity toEntity(BoardEntity boardEntity) {
        return BoardFileEntity.builder()
                .boardFilePath(this.boardFilePath)
                .boardId(boardEntity.getBoardId())
                .build();
    }
}
