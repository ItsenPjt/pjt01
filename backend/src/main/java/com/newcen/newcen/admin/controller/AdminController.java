package com.newcen.newcen.admin.controller;

import com.newcen.newcen.admin.dto.request.AdminValidUserSaveRequestDTO;
import com.newcen.newcen.admin.dto.response.AdminUserResponseDTO;
import com.newcen.newcen.admin.dto.response.AdminValidUserResponseDTO;
import com.newcen.newcen.admin.exception.AdminCustomException;
import com.newcen.newcen.admin.exception.AdminExceptionEnum;
import com.newcen.newcen.admin.service.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;


    // 회원 목록 조회
    @GetMapping("/api/admins")
    public ResponseEntity<?> userList(@AuthenticationPrincipal String userId) {

        if(userId.equals("anonymousUser")) {
            throw new AdminCustomException(AdminExceptionEnum.TOKEN_REQUIRED);
        }

        List<AdminUserResponseDTO> userList = adminService.userList(userId);

        return ResponseEntity
                .ok()
                .body(userList);
    }

    // 회원 삭제
    @DeleteMapping("/api/admins/{deleteId}")
    public ResponseEntity<?> userDelete(@AuthenticationPrincipal String userId,
                                        @PathVariable("deleteId") String deleteId) {

        if(userId.equals("anonymousUser")) {
            throw new AdminCustomException(AdminExceptionEnum.TOKEN_REQUIRED);
        }

        List<AdminUserResponseDTO> userList = adminService.userDelete(userId, deleteId);

        return ResponseEntity
                .ok()
                .body(userList);
    }

    // Valid 회원 목록
    @GetMapping("/api/admins/validuser")
    public ResponseEntity<?> validUserList(@AuthenticationPrincipal String userId) {
        if(userId.equals("anonymousUser")) {
            throw new AdminCustomException(AdminExceptionEnum.TOKEN_REQUIRED);
        }

        List<AdminValidUserResponseDTO> validUserList = adminService.validUserList(userId);

        return ResponseEntity
                .ok()
                .body(validUserList);
    }


    // Valid 이메일 등록
    @PostMapping("/api/admins/validuser")
    public ResponseEntity<?> validUserSave(@AuthenticationPrincipal String userId,
                                           @Validated @RequestBody AdminValidUserSaveRequestDTO requestDTO) {

        if(userId.equals("anonymousUser")) {
            throw new AdminCustomException(AdminExceptionEnum.TOKEN_REQUIRED);
        }

        List<AdminValidUserResponseDTO> validUserList = adminService.validUserSave(userId, requestDTO);

        return ResponseEntity.ok()
                .body(validUserList);
    }

}
