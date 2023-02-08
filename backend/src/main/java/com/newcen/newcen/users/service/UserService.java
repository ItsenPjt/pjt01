package com.newcen.newcen.users.service;

import com.newcen.newcen.common.config.security.TokenProvider;
import com.newcen.newcen.common.entity.UserEntity;
import com.newcen.newcen.common.entity.ValidUserEntity;
import com.newcen.newcen.common.repository.ValidUserRepository;
import com.newcen.newcen.users.dto.request.UserSignUpRequestDTO;
import com.newcen.newcen.users.dto.response.UserSignUpResponseDTO;
import com.newcen.newcen.users.exception.NoRegisteredArgumentsException;
import com.newcen.newcen.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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


}
