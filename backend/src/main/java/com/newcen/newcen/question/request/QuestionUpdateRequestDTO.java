package com.newcen.newcen.question.request;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestionUpdateRequestDTO {

    private String boardTitle;

    private String boardContent;



}
