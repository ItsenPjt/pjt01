package com.newcen.newcen.notice.dto.response;

import lombok.*;

import java.util.List;

@Setter @Getter @ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NoticeListResponseDTO {        // 공지사항 목록 DTO
    private String error;

    private List<NoticeDetailResponseDTO> notices;
}
