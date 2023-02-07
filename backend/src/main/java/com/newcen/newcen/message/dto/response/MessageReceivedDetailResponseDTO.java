package com.newcen.newcen.message.dto.response;

import com.newcen.newcen.message.entity.MessageEntity;
import lombok.*;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageReceivedDetailResponseDTO {

    private String messageTitle;
    private String messageContent;
    private String messageSender;

    public MessageReceivedDetailResponseDTO(MessageEntity entity) {
        this.messageTitle = entity.getMessageTitle();
        this.messageContent = entity.getMessageContent();
        this.messageSender = entity.getMessageSender();
    }



}
