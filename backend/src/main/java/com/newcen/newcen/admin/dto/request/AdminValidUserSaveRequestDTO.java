package com.newcen.newcen.admin.dto.request;

import com.newcen.newcen.common.entity.ValidUserEntity;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
public class AdminValidUserSaveRequestDTO {

    @NotBlank
    @Email
    private String validUserEmail;

    private String validCode;


    public ValidUserEntity toEntity() {
        return ValidUserEntity.builder()
                .validUserEmail(this.validUserEmail)
                .validCode(this.validCode)
                .validActive(1)
                .build();
    }

    public void setValidCode(String validCode) {
        this.validCode = validCode;
    }



}
