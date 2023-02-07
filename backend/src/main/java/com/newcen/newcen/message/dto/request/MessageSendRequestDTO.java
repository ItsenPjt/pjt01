package com.newcen.newcen.message.dto.request;


import lombok.*;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageSendRequestDTO {

    private String messageTitle;
    private String messageContent;
    private String messageSender;
    private String messageReceiver;


}
