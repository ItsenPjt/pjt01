package com.newcen.newcen.users.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.newcen.newcen.common.entity.UserEntity;
import lombok.*;

import java.time.LocalDate;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class AnonymousReviseResponseDTO {

    private String userEmail;
    private String userName;

    @JsonFormat(pattern = "yyyy년 MM월 dd일")
    private LocalDate userRegdate;

    private String message; // 응답 메세지


    // 엔터티를 DTO로 변경
    public AnonymousReviseResponseDTO(UserEntity entity) {
        this.userEmail = entity.getUserEmail();
        this.userName = entity.getUserName();
        this.userRegdate = entity.getUserRegdate();

    }

}
