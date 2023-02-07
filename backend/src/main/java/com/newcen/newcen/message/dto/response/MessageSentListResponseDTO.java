package com.newcen.newcen.message.dto.response;

import lombok.*;

import java.util.List;

@Getter @ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageSentListResponseDTO {

    private List<MessageSentResponseDTO> sentMessageList;

}
