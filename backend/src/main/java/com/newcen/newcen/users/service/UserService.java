package com.newcen.newcen.users.service;

import com.newcen.newcen.common.config.security.TokenProvider;
import com.newcen.newcen.common.repository.ValidUserRepository;
import com.newcen.newcen.users.dto.request.UserSignUpRequestDTO;
import com.newcen.newcen.users.dto.response.UserSignUpResponseDTO;
import com.newcen.newcen.users.exception.NoRegisteredArgumentsException;
import com.newcen.newcen.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor    // 초기화 되지않은 final 또는 @NonNull 이 붙은 필드에 대해 생성자를 생성
public class UserService {

    private final UserRepository userRepository;
    private final ValidUserRepository validUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;


    // 회원가입 처리
//    public UserSignUpResponseDTO create(final UserSignUpRequestDTO userSignUpRequestDTO) {
//        if (userSignUpRequestDTO == null) {
//            throw new NoRegisteredArgumentsException("가입 정보가 없습니다.");
//        }
//
//    }


}
