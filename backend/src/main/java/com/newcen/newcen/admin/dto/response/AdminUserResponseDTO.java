package com.newcen.newcen.admin.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.newcen.newcen.common.entity.UserEntity;
import com.newcen.newcen.common.entity.UserRole;
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
    private UserRole userRole;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate userRegdate;

    public AdminUserResponseDTO(UserEntity entity) {
        this.userId = entity.getUserId();
        this.userEmail = entity.getUserEmail();
        this.userName = entity.getUserName();
        this.userRole = entity.getUserRole();
        this.userRegdate = entity.getUserRegdate();
    }

}
