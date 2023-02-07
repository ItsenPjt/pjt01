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
public class MessageReceivedResponseDTO {

    private long messageId;
    private String messageTitle;
    private String messageSender;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime messageSenddate;

    public MessageReceivedResponseDTO(MessageEntity entity) {
        this.messageId = entity.getMessageId();
        this.messageTitle = entity.getMessageTitle();
        this.messageSender = entity.getMessageSender();
        this.messageSenddate = entity.getMessageSenddate();
    }






}
