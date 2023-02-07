package com.newcen.newcen.users.dto.request;

import com.newcen.newcen.common.entity.UserEntity;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter @Setter @ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "userEmail")    // email로만 비교
@Builder

// 회원 가입 시 클라이언트가 보낸 데이터를 담는 객체
public class UserSignUpDTO {

    @NotBlank   // @NotBlank - null 과 "" 과 " " 모두 허용하지 않음
    @Email
    private String userEmail;

    @NotBlank
    @Size(min = 8, max = 20)
    private String userPassword;

    @NotBlank
    @Size(min = 2, max = 10)
    private String userName;

    @NotBlank
    @Size(min = 2, max = 10)
    private String validCode;


    // UserEntity 로 변경하는 메서드
    public UserEntity toEntity() {
        return UserEntity.builder()
                .userEmail(this.userEmail)
                .userPassword(this.userPassword)
                .userName(this.userName)
                .build();
    }

}
