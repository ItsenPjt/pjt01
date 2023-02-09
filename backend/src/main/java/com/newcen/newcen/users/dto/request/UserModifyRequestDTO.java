package com.newcen.newcen.users.dto.request;

import com.newcen.newcen.common.entity.UserEntity;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class UserModifyRequestDTO {

    // 내정보 페이지 비밀번호 변경
    @NotBlank   // @NotBlank - null 과 "" 과 " " 모두 허용하지 않음
    @Email
    private String userEmail;

    @NotBlank
    @Size(min = 8, max = 20)
    private String userPassword;

    @NotBlank
    @Size(min = 2, max = 10)
    private String userName;


    // UserEntity 로 변경하는 메서드
    public UserEntity mdEntity() {
        return UserEntity.builder()
                .userPassword(this.userPassword)
                .build();
    }

}
