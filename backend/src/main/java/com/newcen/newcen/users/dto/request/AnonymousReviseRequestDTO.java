package com.newcen.newcen.users.dto.request;

import com.newcen.newcen.common.entity.UserEntity;
import com.newcen.newcen.common.entity.ValidUserEntity;
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
public class AnonymousReviseRequestDTO {

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

    @NotBlank
    @Size(min = 2, max = 10)
    private String validCode;


}
