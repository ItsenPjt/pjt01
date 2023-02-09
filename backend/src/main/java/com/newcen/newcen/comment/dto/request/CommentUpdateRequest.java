package com.newcen.newcen.comment.dto.request;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentUpdateRequest {

    private String commentContent;

}
