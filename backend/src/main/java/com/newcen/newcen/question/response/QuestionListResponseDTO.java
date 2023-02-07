package com.newcen.newcen.question.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
@ToString
public class QuestionListResponseDTO {

    private String error;
    private String statusCode;
    private List<QuestionResponseDTO> data;
}
