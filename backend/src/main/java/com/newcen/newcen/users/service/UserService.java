package com.newcen.newcen.users.service;

import com.newcen.newcen.admin.exception.AdminCustomException;
import com.newcen.newcen.admin.exception.AdminExceptionEnum;
import com.newcen.newcen.common.config.security.TokenProvider;
import com.newcen.newcen.common.entity.UserEntity;
import com.newcen.newcen.common.entity.UserRole;
import com.newcen.newcen.common.entity.ValidUserEntity;
import com.newcen.newcen.common.repository.ValidUserRepository;
import com.newcen.newcen.users.dto.request.AnonymousReviseRequestDTO;
import com.newcen.newcen.users.dto.request.UserModifyRequestDTO;
import com.newcen.newcen.users.dto.request.UserSignUpRequestDTO;
import com.newcen.newcen.users.dto.response.*;
import com.newcen.newcen.users.exception.DuplicatedEmailException;
import com.newcen.newcen.users.exception.NoRegisteredArgumentsException;
import com.newcen.newcen.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor    // 초기화 되지않은 final 또는 @NonNull 이 붙은 필드에 대해 생성자를 생성
@DynamicInsert
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

        return retrieve(email);

    }

    private UserSignUpResponseDTO retrieve(String email) {
        UserEntity user = userRepository.findByUserEmail(email);
        return new UserSignUpResponseDTO(user);
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
        log.info("{}님의 토큰 : {}",originalUser.getUserName(), token);
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
            log.info("변경된 계정 - user_email : {}", savedUser.getUserEmail());

        });

        Optional<UserEntity> result = userRepository.findByUserId(userId);

        return new UserModifyResponseDTO(result.get());

    }

    // 익명 사용자 비밀번호 찾기 시 수정
    public AnonymousReviseResponseDTO update(
            AnonymousReviseRequestDTO anonymousReviseRequestDTO) {

        if (anonymousReviseRequestDTO == null) {
            throw new NoRegisteredArgumentsException("Unknown UserInfo - 알 수 없는 회원정보 입니다.");
        }

        final String Email = anonymousReviseRequestDTO.getUserEmail();
        final String Name = anonymousReviseRequestDTO.getUserName();
        final String validCode = anonymousReviseRequestDTO.getValidCode();

        final boolean authorizedUser =
                userRepository.existsByUserEmailAndUserName(Email, Name);

        final boolean verifiedUser =
                validUserRepository.existsByValidCode(validCode);


        UserEntity loadUserEmail = userRepository.findByUserEmail(Email);


        if (authorizedUser && verifiedUser) {
            Optional<UserEntity> targetEntity = Optional.of(userRepository.findByUserEmail(loadUserEmail.getUserEmail()));

            targetEntity.ifPresent(entity -> {

                // 패스워드 인코딩
                String rawPassword = anonymousReviseRequestDTO.getUserPassword();   // 새로운 수정된 평문 암호
                String encodePassword = passwordEncoder.encode(rawPassword);    // 암호화 처리
                entity.setUserPassword(encodePassword);

                UserEntity savedUser = userRepository.save(entity);

                log.info(
                        "익명 사용자 비번찾기 정보 수정 성공..!!! - user_id : {}", savedUser.getUserId());
                log.info("변경된 계정 - userEmail : {}", savedUser.getUserEmail());
            });

        } else {
            log.warn("입력 정보를 확인해주세요. - user_email : {}", Email);
            log.warn("입력 정보를 확인해주세요. - user_name : {}", Name);
            log.warn("입력 정보를 확인해주세요. - valid_code : {}", validCode);

            throw new RuntimeException("Unknown UserInfo - 입력 정보를 확인해주세요.");

        }

        UserEntity anonymousUser = userRepository.findByUserEmail(Email);

        return new AnonymousReviseResponseDTO(anonymousUser);

    }

    // 회원 탈퇴 (회원정보 삭제)
    @Transactional
    public UserDeleteResponseDTO delete(
            final String deleteId) {

        try {

            String delEmail = userRepository.selectUserEmail(deleteId);

            userRepository.deleteById(deleteId);  // 로그인된 회원 UUID로 회원정보 삭제
            log.info("User Delete Complete - UserId : {}", deleteId);

            boolean nonExistentId = userRepository.existsById(deleteId); // 삭제된 UUID 존재 여부 조회

            if (!nonExistentId) {

                validUserRepository.deleteByValidUserEmail(delEmail);
                log.info("validUserEmail Delete Complete - ValidUserEmail : {}", delEmail);

            }

        } catch (Exception e) {
            log.error("delEmail 이 존재하지 않아 삭제에 실패했습니다. - ID: {}, err: {}"
                    , deleteId, e.getMessage());

            throw new RuntimeException("delEmail 이 존재하지 않아 삭제에 실패했습니다.");

        }

        Optional<ValidUserEntity> endUser = validUserRepository.findById(deleteId);

        String message = "";

        if (!endUser.isPresent()) {
            message = "회원 탈퇴 처리가 완료되었습니다.";
            log.info("{}", message);
        } else {
            message = "비정상적인 처리입니다.";
            log.info("{}", message);
        }

        return new UserDeleteResponseDTO(!endUser.isPresent(), message);

    }

}
