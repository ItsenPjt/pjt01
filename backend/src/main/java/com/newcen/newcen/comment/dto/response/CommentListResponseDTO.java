package com.newcen.newcen.comment.dto.response;

import com.newcen.newcen.question.response.QuestionResponseDTO;
import lombok.*;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@Builder
@ToString
public class CommentListResponseDTO {
    private String error;
    private String statusCode;
    private List<CommentResponseDTO> data;

}
