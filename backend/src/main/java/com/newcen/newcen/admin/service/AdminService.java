package com.newcen.newcen.admin.service;

import com.newcen.newcen.admin.dto.SendEmailDTO;
import com.newcen.newcen.admin.dto.request.AdminValidUserSaveRequestDTO;
import com.newcen.newcen.admin.dto.response.AdminUserResponseDTO;
import com.newcen.newcen.admin.dto.response.AdminValidUserResponseDTO;
import com.newcen.newcen.admin.exception.AdminCustomException;
import com.newcen.newcen.admin.exception.AdminExceptionEnum;
import com.newcen.newcen.admin.repository.AdminRepository;
import com.newcen.newcen.common.entity.UserEntity;
import com.newcen.newcen.common.entity.UserRole;
import com.newcen.newcen.common.entity.ValidUserEntity;
import com.newcen.newcen.common.repository.ValidUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;
    private final ValidUserRepository validUserRepository;
    private final EmailService emailService;

    public List<AdminUserResponseDTO> userList(final String userId) {

        UserEntity admin = adminRepository.findById(userId).orElseThrow(() -> {
            throw new AdminCustomException(AdminExceptionEnum.USER_NOT_EXIST);
        });

        if(!admin.getUserRole().equals(UserRole.ADMIN)) {
            throw new AdminCustomException(AdminExceptionEnum.ACCESS_FORBIDDEN);
        }

        List<UserEntity> userList = adminRepository.findAll();

        if(userList.isEmpty()) {
            throw new AdminCustomException(AdminExceptionEnum.EMPTY_LIST);
        }

        List<AdminUserResponseDTO> responseList = new ArrayList<>();

        for(UserEntity user : userList) {
            responseList.add(new AdminUserResponseDTO(user));
        }

        return responseList;
    }

    @Transactional
    public List<AdminUserResponseDTO> userDelete(final String userId, final String deleteId) {

        UserEntity admin = adminRepository.findById(userId).orElseThrow(() -> {
            throw new AdminCustomException(AdminExceptionEnum.USER_NOT_EXIST);
        });

        if(!admin.getUserRole().equals(UserRole.ADMIN)) {
            throw new AdminCustomException(AdminExceptionEnum.ACCESS_FORBIDDEN);
        }

        UserEntity user = adminRepository.findById(deleteId).orElseThrow(() -> {
            throw new AdminCustomException(AdminExceptionEnum.USER_NOT_EXIST);
        });

        String email = user.getUserEmail();
        adminRepository.deleteById(deleteId);
        validUserRepository.deleteByValidUserEmail(email);

        return userList(userId);
    }


    public List<AdminValidUserResponseDTO> validUserList(final String userId) {

        UserEntity admin = adminRepository.findById(userId).orElseThrow(() -> {
            throw new AdminCustomException(AdminExceptionEnum.USER_NOT_EXIST);
        });

        if(!admin.getUserRole().equals(UserRole.ADMIN)) {
            throw new AdminCustomException(AdminExceptionEnum.ACCESS_FORBIDDEN);
        }

        List<ValidUserEntity> validUserList = validUserRepository.findAll();

        if(validUserList.isEmpty()) {
            throw new AdminCustomException(AdminExceptionEnum.EMPTY_LIST);
        }

        List<AdminValidUserResponseDTO> responseList = new ArrayList<>();

        for(ValidUserEntity validUser : validUserList) {
            responseList.add(new AdminValidUserResponseDTO(validUser));
        }

        return responseList;
    }


    public List<AdminValidUserResponseDTO> validUserSave(final String userId, final AdminValidUserSaveRequestDTO requestDTO) {

        UserEntity admin = adminRepository.findById(userId).orElseThrow(() -> {
            throw new AdminCustomException(AdminExceptionEnum.USER_NOT_EXIST);
        });

        if(!admin.getUserRole().equals(UserRole.ADMIN)) {
            throw new AdminCustomException(AdminExceptionEnum.ACCESS_FORBIDDEN);
        }

        String validCode = RandomStringUtils.randomNumeric(6);
        requestDTO.setValidCode(validCode);
        ValidUserEntity savedValidUser = validUserRepository.save(requestDTO.toEntity());

        SendEmailDTO emailDTO = new SendEmailDTO(requestDTO);
        emailService.sendSimpleMessage(emailDTO);

        return validUserList(userId);
    }

    public List<AdminValidUserResponseDTO> validUserDelete(final String userId, final String deleteId) {

        UserEntity admin = adminRepository.findById(userId).orElseThrow(() -> {
            throw new AdminCustomException(AdminExceptionEnum.USER_NOT_EXIST);
        });

        if(!admin.getUserRole().equals(UserRole.ADMIN)) {
            throw new AdminCustomException(AdminExceptionEnum.ACCESS_FORBIDDEN);
        }

        validUserRepository.deleteById(deleteId);

        return validUserList(userId);
    }



}
