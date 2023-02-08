package com.newcen.newcen.question.request;

import com.newcen.newcen.common.entity.BoardEntity;
import com.newcen.newcen.common.entity.BoardFileEntity;
import com.newcen.newcen.common.utill.BaseResponseBody;
import lombok.*;


@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestionFileRequestDTO  {

    String boardFilePath;

    public BoardFileEntity toEntity(){
        return BoardFileEntity.builder()
                .boardFilePath(this.boardFilePath)
                .build();
    }


}
