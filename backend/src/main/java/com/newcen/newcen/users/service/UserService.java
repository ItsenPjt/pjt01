package com.newcen.newcen.users.service;

import com.newcen.newcen.common.config.security.TokenProvider;
import com.newcen.newcen.common.entity.UserEntity;
import com.newcen.newcen.common.entity.ValidUserEntity;
import com.newcen.newcen.common.repository.ValidUserRepository;
import com.newcen.newcen.users.dto.request.AnonymousReviseRequestDTO;
import com.newcen.newcen.users.dto.request.UserModifyRequestDTO;
import com.newcen.newcen.users.dto.request.UserSignUpRequestDTO;
import com.newcen.newcen.users.dto.response.LoginResponseDTO;
import com.newcen.newcen.users.dto.response.UserModifyResponseDTO;
import com.newcen.newcen.users.dto.response.UserSignUpResponseDTO;
import com.newcen.newcen.users.exception.NoRegisteredArgumentsException;
import com.newcen.newcen.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor    // 초기화 되지않은 final 또는 @NonNull 이 붙은 필드에 대해 생성자를 생성
public class UserService {

    private final UserRepository userRepository;
    private final ValidUserRepository validUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;


    // 회원가입 처리
    @Transactional
    public UserSignUpResponseDTO create(final UserSignUpRequestDTO userSignUpRequestDTO) {
        if (userSignUpRequestDTO == null) {
            throw new NoRegisteredArgumentsException("Nonexistent UserInfo - 가입 정보가 없습니다.");
        }

        final String email = userSignUpRequestDTO.getUserEmail();
        final String code = userSignUpRequestDTO.getValidCode();
        final boolean compareResult =
                validUserRepository.existsByValidUserEmailAndValidCodeAndValidActive(email, code, 1);

        if (!compareResult) {
            log.warn("********** - UserInfo unprepared - {}", compareResult);
            throw new NoRegisteredArgumentsException("Nonexistent UserInfo - 등록되지 않은 회원정보입니다.");
        }

        log.info("********** - UserInfo available to use - {}", compareResult);

        // 패스워드 인코딩
        String rawPassword = userSignUpRequestDTO.getUserPassword();   // 평문 암호
        String encodePassword = passwordEncoder.encode(rawPassword);    // 암호화 처리
        userSignUpRequestDTO.setUserPassword(encodePassword);

        UserEntity savedUser = userRepository.save(userSignUpRequestDTO.toEntity());

        log.info("********** - 회원가입 성공..!!! - user_id : {}", savedUser.getUserId());

        validUserRepository.updateSetActive(email, code);

        ValidUserEntity updatedActive =
                validUserRepository.findByValidUserEmailAndValidCode(email, code);

        log.info("********** - User ActiveValue Change Complete - valid_user_active : {}", updatedActive);

        return new UserSignUpResponseDTO(savedUser);

    }


    // 로그인 처리 및 검증
    public LoginResponseDTO getByCredentials(
            final String email,
            final String rawPassword) {

        // 입력한 이메일을 통해 회원정보 조회
        UserEntity originalUser = userRepository.findByUserEmail(email);

        if (originalUser == null) {
            throw new RuntimeException("가입된 회원이 아닙니다.");
        }
        // 패스워드 검증 (입력 비번, DB에 저장된 비번)
        if (!passwordEncoder.matches(rawPassword, originalUser.getUserPassword())) {
            throw new RuntimeException("비밀번호가 틀렸습니다.");
        }

        log.info("{}님 로그인 성공!", originalUser.getUserName());

        // 토큰 발급
        String token = tokenProvider.createToken(originalUser);

        return new LoginResponseDTO(originalUser, token);

    }

    // 내정보 수정
    public UserModifyResponseDTO update(
            final String userId,
            final UserModifyRequestDTO userModifyRequestDTO) {

        Optional<UserEntity> targetEntity = userRepository.findByUserId(userId);

        targetEntity.ifPresent(entity -> {

            // 패스워드 인코딩
            String rawPassword = userModifyRequestDTO.getUserPassword();   // 새로운 수정된 평문 암호
            String encodePassword = passwordEncoder.encode(rawPassword);    // 암호화 처리
            entity.setUserPassword(encodePassword);

            UserEntity savedUser = userRepository.save(entity);

            log.info(
                    "내정보 수정 성공..!!! - user_id : {}", savedUser.getUserId());
            log.info("변경된 계정 - user_Email : {}", savedUser.getUserEmail());

        });

        Optional<UserEntity> result = userRepository.findByUserId(userId);

        return new UserModifyResponseDTO(result.get());

    }

    // 익명 사용자 비밀번호 찾기 시 수정
//    public AnonymousReviseRequestDTO update(
//            AnonymousReviseRequestDTO anonymousReviseRequestDTO) {
//        if (anonymousReviseRequestDTO == null) {
//            throw new NoRegisteredArgumentsException("mysterious UserInfo - 알 수 없는 회원정보 입니다.");
//        }
//
//        final String userEmail = anonymousReviseRequestDTO.getUserEmail();
//        final String userName = anonymousReviseRequestDTO.getUserName();
//        final String userPassword = anonymousReviseRequestDTO.getUserPassword();
//
//        final String validCode = anonymousReviseRequestDTO.getValidCode();
//
//        final boolean authorizedUser =
//                validUserRepository.existsByValidUserEmailAndValidCodeAndValidActive(email, code, 1);
//
//    }

}
