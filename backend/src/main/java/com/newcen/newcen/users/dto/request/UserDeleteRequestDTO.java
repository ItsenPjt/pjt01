package com.newcen.newcen.users.dto.request;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode

public class UserDeleteRequestDTO {



    @NotBlank   // @NotBlank - null 과 "" 과 " " 모두 허용하지 않음
    @Email
    private String userEmail;

}
