package com.newcen.newcen.users.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.newcen.newcen.common.entity.UserEntity;
import com.newcen.newcen.common.entity.UserRole;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "userEmail")    // email로만 비교
@Builder

public class UserSignUpResponseDTO {

    private String userEmail;

    private String userName;

    private UserRole userRole;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime userRegdate;


    // UserEntity 를 UserSignUpResponseDTO 로 변경하는 생성자
    public UserSignUpResponseDTO(UserEntity entity) {

        this.userEmail = entity.getUserEmail();
        this.userName = entity.getUserName();
        this.userRole = entity.getUserRole();
        this.userRegdate = entity.getUserRegdate();

    }

}
