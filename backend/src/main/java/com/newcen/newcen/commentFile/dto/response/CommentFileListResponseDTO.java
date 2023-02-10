package com.newcen.newcen.commentFile.dto.response;

import lombok.*;

import java.util.List;
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentFileListResponseDTO {
    private String error;
    private int statusCode;
    private List<CommentFileResponseDTO> data;
}
