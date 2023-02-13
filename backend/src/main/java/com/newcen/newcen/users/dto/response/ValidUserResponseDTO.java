package com.newcen.newcen.users.dto.response;

import com.newcen.newcen.common.entity.UserEntity;
import com.newcen.newcen.common.entity.ValidUserEntity;
import lombok.*;

@Setter @Getter @ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder

public class ValidUserResponseDTO {

    private String error;   // 에러 발생 시 클라이언트에게 보낼 메세지

    private String validUserEmail;
    private String validCode;


    // UserEntity 를 UserSignUpResponseDTO 로 변경하는 생성자
    public ValidUserResponseDTO(ValidUserEntity entity) {

        this.validUserEmail = entity.getValidUserEmail();
        this.validCode = entity.getValidCode();

    }

}
