package com.newcen.newcen.users.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.newcen.newcen.common.entity.UserEntity;
import com.newcen.newcen.common.entity.ValidUserEntity;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Setter @Getter @ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode

public class UserDeleteResponseDTO {

    private String userEmail;

    private String userName;

    @JsonFormat(pattern = "yyyy년 MM월 dd일")
    private LocalDate userRegdate;

    private String message; // 응답 메세지

}
