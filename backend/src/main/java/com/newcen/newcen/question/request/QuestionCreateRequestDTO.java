package com.newcen.newcen.question.request;

import com.newcen.newcen.common.entity.BoardEntity;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestionCreateRequestDTO {



    public BoardEntity boardEntity(){
        return BoardEntity.builder()
                .build();
    }

}
