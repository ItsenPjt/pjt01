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
public class UserModifyResponseDTO {

    private String message; // 응답 메세지


}
