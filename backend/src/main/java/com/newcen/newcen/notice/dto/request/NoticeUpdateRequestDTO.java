package com.newcen.newcen.notice.dto.request;

import com.newcen.newcen.common.entity.BoardCommentIs;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class NoticeUpdateRequestDTO {

    private String boardTitle;

    private String boardWriter;

    private String boardContent;

    private BoardCommentIs boardCommentIs;
}
