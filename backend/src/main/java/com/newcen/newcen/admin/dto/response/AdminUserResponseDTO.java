package com.newcen.newcen.admin.dto.response;

import com.newcen.newcen.common.entity.UserEntity;
import lombok.*;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Getter
@Transactional
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdminUserResponseDTO {

    private String userId;
    private String userEmail;
    private String userName;
    private LocalDate userRegdate;

    public AdminUserResponseDTO(UserEntity entity) {
        this.userId = entity.getUserId();
        this.userEmail = entity.getUserEmail();
        this.userName = entity.getUserName();
        this.userRegdate = entity.getUserRegdate();
    }

}
