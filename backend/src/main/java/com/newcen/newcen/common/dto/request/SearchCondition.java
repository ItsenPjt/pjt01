package com.newcen.newcen.common.dto.request;


import com.newcen.newcen.common.entity.BoardType;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SearchCondition {
    private String boardTitle;

    private String boardContent;

    private BoardType boardType;

    private String boardWriter;

}
