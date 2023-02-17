package com.newcen.newcen.question.request;

import com.newcen.newcen.common.entity.BoardFileEntity;
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
