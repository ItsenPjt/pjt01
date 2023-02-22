package com.newcen.newcen.message.dto.request;


import com.newcen.newcen.common.entity.UserEntity;
import com.newcen.newcen.message.entity.MessageEntity;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageSendRequestDTO {

    @NotBlank
    @Size(min = 1, max = 20)
    private String messageTitle;
    @NotBlank
    @Size(min = 1, max = 200)
    private String messageContent;


    public MessageEntity toEntity(UserEntity sender, UserEntity receiver) {
        return MessageEntity.builder()
                .messageTitle(this.messageTitle)
                .messageContent(this.messageContent)
                .messageSender(sender.getUserName())
                .messageReceiver(receiver.getUserName())
                .sender(sender)
                .receiver(receiver)
                .build();
    }


}
