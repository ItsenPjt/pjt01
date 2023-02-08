package com.newcen.newcen.message.dto.response;

import com.newcen.newcen.common.entity.UserEntity;
import lombok.*;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
public class MessageReceiverResponseDTO {

    private String userId;
    private String userName;
    private String userEmail;

    public MessageReceiverResponseDTO(UserEntity entity) {
        this.userId = entity.getUserId();
        this.userName = entity.getUserName();
        this.userEmail = entity.getUserEmail();
    }


}
