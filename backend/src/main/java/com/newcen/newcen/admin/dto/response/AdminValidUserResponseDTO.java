package com.newcen.newcen.admin.dto.response;

import com.newcen.newcen.common.entity.ValidUserEntity;
import lombok.*;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotBlank;
import java.awt.desktop.OpenFilesEvent;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "validUserEmail")
@Builder
public class AdminValidUserResponseDTO {

    private String validUserId;
    private String validUserEmail;
    private int validActive;

    public AdminValidUserResponseDTO(ValidUserEntity entity) {
        this.validUserId = entity.getValidUserId();
        this.validUserEmail = entity.getValidUserEmail();
        this.validActive = entity.getValidActive();
    }


}
