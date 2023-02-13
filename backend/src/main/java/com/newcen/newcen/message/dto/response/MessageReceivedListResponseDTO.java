package com.newcen.newcen.message.dto.response;

import lombok.*;

import java.util.List;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageReceivedListResponseDTO {


    private List<MessageReceivedResponseDTO> receivedMessageList;




}
