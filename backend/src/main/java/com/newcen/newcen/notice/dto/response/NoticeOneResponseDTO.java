package com.newcen.newcen.notice.dto.response;

import com.newcen.newcen.common.entity.BoardFileEntity;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Setter @Getter @ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NoticeOneResponseDTO {     // 공지사항 1개 내용 DTO (파일, 댓글 추가해야 함)

    private String error;

    private List<NoticeDetailResponseDTO> noticeDetails;

    // 파일 경로
    private List<BoardFileEntity> boardFileEntityList;
}
