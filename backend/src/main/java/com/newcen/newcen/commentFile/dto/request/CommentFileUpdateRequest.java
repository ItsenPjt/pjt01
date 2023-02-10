package com.newcen.newcen.commentFile.dto.request;


import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentFileUpdateRequest {
    @NotBlank
    private String commentFilePath;
}
