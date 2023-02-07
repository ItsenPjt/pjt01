package com.newcen.newcen.message.dto.response;

import com.newcen.newcen.message.entity.MessageEntity;
import lombok.*;
import org.aspectj.bridge.Message;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageSentDetailResponseDTO {


    private String messageTitle;
    private String messageContent;
    private String messageReceiver;

    public MessageSentDetailResponseDTO(MessageEntity entity) {
        this.messageTitle = entity.getMessageTitle();
        this.messageContent = entity.getMessageContent();
        this.messageReceiver = entity.getMessageReceiver();
    }

}
