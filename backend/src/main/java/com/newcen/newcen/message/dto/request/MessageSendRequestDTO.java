package com.newcen.newcen.message.dto.request;


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
    @NotBlank
    private String senderId;
    @NotBlank
    private String messageSender;
    @NotBlank
    private String receiverId;
    @NotBlank
    private String messageReceiver;


}
