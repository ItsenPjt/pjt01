package com.newcen.newcen.message.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.newcen.newcen.message.entity.MessageEntity;
import lombok.*;

import java.time.LocalDateTime;

@Getter @ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
public class MessageSentResponseDTO {

    private long messageId;
    private String messageTitle;
    private String messageReceiver;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime messageSenddate;

    public MessageSentResponseDTO(MessageEntity entity) {
        this.messageId = entity.getMessageId();
        this.messageTitle = entity.getMessageTitle();
        this.messageReceiver = entity.getMessageReceiver();
        this.messageSenddate = entity.getMessageSenddate();
    }






}
