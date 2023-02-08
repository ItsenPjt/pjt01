package com.newcen.newcen.users.dto.response;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.newcen.newcen.common.entity.UserEntity;
import lombok.*;

import java.time.LocalDate;

@Setter @Getter @ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class LoginResponseDTO {

    private String userEmail;
    private String userName;

    @JsonFormat(pattern = "yyyy년 MM월 dd일")
    private LocalDate userRegdate;

    private String token;   // 인증 토큰

    private String message; // 응답 메세지

    // 엔터티를 DTO로 변경
    public LoginResponseDTO(UserEntity userEntity, String token) {
        this.userEmail = userEntity.getUserEmail();
        this.userName = userEntity.getUserName();
        this.userRegdate = LocalDate.from(userEntity.getUserRegdate());
        this.token = token;
    }

}

