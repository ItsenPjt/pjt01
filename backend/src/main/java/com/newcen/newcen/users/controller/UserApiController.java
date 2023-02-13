package com.newcen.newcen.users.controller;

import com.newcen.newcen.users.dto.request.AnonymousReviseRequestDTO;
import com.newcen.newcen.users.dto.request.LoginRequestDTO;
import com.newcen.newcen.users.dto.request.UserModifyRequestDTO;
import com.newcen.newcen.users.dto.request.UserSignUpRequestDTO;
import com.newcen.newcen.users.dto.response.*;
import com.newcen.newcen.users.exception.DuplicatedEmailException;
import com.newcen.newcen.users.exception.NoRegisteredArgumentsException;
import com.newcen.newcen.users.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@Slf4j
@RequiredArgsConstructor // 초기화 되지않은 final 또는 @NonNull 이 붙은 필드에 대해 생성자를 생성
//@RequestMapping("/api")
public class UserApiController {

    private final UserService userService;

    // 회원가입 요청처리
    @PostMapping("/api/user/signup")
    public ResponseEntity<?> signup(
            @Validated @RequestBody
            UserSignUpRequestDTO signUpDTO, BindingResult result) {

        log.info("/api/user/signup POST! - {}", signUpDTO);

        if (result.hasErrors()) {
            log.warn(result.toString());
            return ResponseEntity
                    .badRequest()
                    .body(result.toString());
        }

        try {
            UserSignUpResponseDTO responseDto = userService.create(signUpDTO);

            return ResponseEntity
                    .ok()
                    .body(responseDto);

        } catch (NoRegisteredArgumentsException e) {
            // 예외 상황 2가지 (dto가 null인 문제)
            log.warn("가입 정보를 다시 확인하세요.");

            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());

        }

    } // signup()


    // 로그인 요청 처리
    @PostMapping("/api/user/join")
    public ResponseEntity<?> join(
            @Validated @RequestBody
            LoginRequestDTO requestDTO, BindingResult result) {

        log.info("/api/user/join POST! - {}", requestDTO);

        if (result.hasErrors()) {
            log.warn(result.toString());
            return ResponseEntity
                    .badRequest()
                    .body(LoginResponseDTO.builder()
                            .message("아이디(로그인 전용 아이디) 또는 비밀번호를 잘못 입력했습니다.\n" +
                                    "입력하신 내용을 다시 확인해주세요.")
                            .build()
                    );
        }

        try {
            LoginResponseDTO userInfo = userService.getByCredentials(
                    requestDTO.getUserEmail(),
                    requestDTO.getUserPassword()
            );

            return ResponseEntity
                    .ok()
                    .body(userInfo);

        } catch (RuntimeException e) {
            return ResponseEntity
                    .badRequest()
                    .body(LoginResponseDTO.builder()
                            .message(e.getMessage())
                            .build()
                    );
        }

    } // join()


    // 내정보 변경 요청
    @RequestMapping(
            value = "/api/user/signset/{id}"
            , method = {RequestMethod.PUT, RequestMethod.PATCH}
    )
    public ResponseEntity<?> updateUser(
            @AuthenticationPrincipal String userId  // @AuthenticationPrincipal 로그인 정보를 받아옴
            , @PathVariable("id") String pathUserId // URL 경로에서 id를 가져옴
            , @Validated @RequestBody UserModifyRequestDTO requestDTO
            , BindingResult result
            , HttpServletRequest request    // PUT 인지, PATCH 인지 요청정보 알 수 있음
            ) {

        if (userId.equals("anonymousUser")) {
            throw new DuplicatedEmailException("로그인이 필요한 서비스입니다.");
        }

        if (result.hasErrors()) {
            return ResponseEntity.badRequest()
                    .body(result.getFieldError());
        }

        log.info("/api/user/{} {} request", pathUserId, request.getMethod());
        log.info("modifying dto : {}", requestDTO);

        try {
            UserModifyResponseDTO responseDTO = userService.update(userId, requestDTO);
            return ResponseEntity.ok()
                    .body(responseDTO);
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(UserListResponseDTO.builder().error(e.getMessage()));
        }

    } // updateUser()


    // 익명 사용자 정보 수정 요청
    @RequestMapping(
            value = "/api/user/findset/{id}"
            , method = {RequestMethod.PUT, RequestMethod.PATCH}
    )
    public ResponseEntity<?> updateAnonymous(
            @Validated @RequestBody
            AnonymousReviseRequestDTO requestDTO, BindingResult result,
            HttpServletRequest request      // PUT 인지, PATCH 인지 요청정보 알 수 있음
    ){

        if (result.hasErrors()) {
            return ResponseEntity.badRequest()
                    .body(result.getFieldError());
        }

        log.info("/api/user/{} {} request", requestDTO, request.getMethod());

        try {
            AnonymousReviseResponseDTO responseDTO = userService.update(requestDTO);
            return ResponseEntity.ok()
                    .body(responseDTO);
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(UserListResponseDTO.builder().error(e.getMessage()));
        }

    } // updateAnonymous()


    // 회원탈퇴(UserEntity, ValidUserEntity 회원정보 삭제)
    @DeleteMapping("/api/user/signout/{id}")
    public ResponseEntity<?> deleteUser(
            @AuthenticationPrincipal String userId  // @AuthenticationPrincipal 로그인 정보를 받아옴
            , @PathVariable("id") String deleteId  // URL 경로에서 id를 가져옴
    ) {

        log.info("/api/user/{} DELETE request", userId);

        if (deleteId == null || deleteId.trim().equals("")) {
            return ResponseEntity
                    .badRequest()
                    .body(UserListResponseDTO.builder().error("삭제하려는 ID를 전달해주세요"));
        }

        try {

            UserDeleteResponseDTO responseDTO = userService.delete(deleteId);

            return ResponseEntity.ok()
                    .body(responseDTO);

        } catch (Exception e) {

            return ResponseEntity.internalServerError()
                    .body(UserListResponseDTO.builder().error(e.getMessage()));
            // e.getMessage() 는 UserService 에서 delete 메소드의 throw new RuntimeException(); 를 의미

        }

    } // deleteUser()


} // UserApiController()


