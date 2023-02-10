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

    private int validActive = 1;


    public ValidUserEntity toEntity() {
        return ValidUserEntity.builder()
                .validUserEmail(this.validUserEmail)
                .validCode(this.validCode)
                .validActive(validActive)
                .build();
    }

    public void setValidCode(String validCode) {
        this.validCode = validCode;
    }



}
