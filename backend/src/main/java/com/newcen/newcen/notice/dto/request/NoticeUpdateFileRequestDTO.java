package com.newcen.newcen.notice.dto.request;

import lombok.*;

@Setter @Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class NoticeUpdateFileRequestDTO {

    private String boardFilePath;
}
