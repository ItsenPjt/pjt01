package com.newcen.newcen.admin.dto;

import com.newcen.newcen.admin.dto.request.AdminValidUserSaveRequestDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SendEmailDTO {

    private String address;
    private String title;
    private String content;

    public SendEmailDTO(AdminValidUserSaveRequestDTO requestDTO) {
        this.address = requestDTO.getValidUserEmail();
        this.title = "아이티센 인사팀입니다";
        this.content = "최종 합격을 축하드립니다!\n 신입 사원 전용 사이트 가입 시 필요한 코드입니다:\n" + requestDTO.getValidCode();


    }

}
