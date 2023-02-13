package com.newcen.newcen.faq.service;

import com.newcen.newcen.common.entity.BoardEntity;
import com.newcen.newcen.common.entity.BoardType;
import com.newcen.newcen.common.entity.UserEntity;
import com.newcen.newcen.common.entity.UserRole;
import com.newcen.newcen.faq.dto.request.FaqSaveRequestDTO;
import com.newcen.newcen.faq.dto.request.FaqUpdateRequestDTO;
import com.newcen.newcen.faq.dto.response.FaqDetailResponseDTO;
import com.newcen.newcen.faq.dto.response.FaqResponseDTO;
import com.newcen.newcen.faq.exception.FaqCustomException;
import com.newcen.newcen.faq.exception.FaqExceptionEnum;
import com.newcen.newcen.faq.repository.FaqRepository;
import com.newcen.newcen.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class FaqService {

    private final FaqRepository faqRepository;

    private final UserRepository userRepository;

    // FAQ 목록 조회
    public List<FaqResponseDTO> faqList() {

        List<BoardEntity> entityList = faqRepository.findByBoardType(BoardType.FAQ);

        if(entityList.isEmpty() ) {
            throw new FaqCustomException(FaqExceptionEnum.EMPTY_LIST);
        }

        List<FaqResponseDTO> faqList = new ArrayList<>();

        for(BoardEntity entity : entityList) {
            faqList.add(new FaqResponseDTO(entity));
        }

        return faqList;
    }

    // FAQ 상세 조회
    public FaqDetailResponseDTO faqDetail(final Long boardId) {

        BoardEntity faqEntity = faqRepository.findById(boardId).orElseThrow(() -> {
            throw new FaqCustomException(FaqExceptionEnum.USER_NOT_EXIST);
        });

        return new FaqDetailResponseDTO(faqEntity);
    }

    // FAQ 등록
    public List<FaqResponseDTO> faqSave(final String userId, final FaqSaveRequestDTO faqSaveRequestDTO) {

        UserEntity user = userRepository.findByUserId(userId).orElseThrow(() -> {
            throw new FaqCustomException(FaqExceptionEnum.USER_NOT_EXIST);
        });

        if(!user.getUserRole().equals(UserRole.ADMIN)) {
            throw new FaqCustomException(FaqExceptionEnum.ACCESS_FORBIDDEN);
        }

        BoardEntity faq  = faqSaveRequestDTO.toEntity(user);
        faqRepository.save(faq);

        return faqList();
    }

    // FAQ 수정
    public FaqDetailResponseDTO faqUpdate(final String userId, final FaqUpdateRequestDTO faqUpdateRequestDTO) {

        UserEntity user = userRepository.findByUserId(userId).orElseThrow(() -> {
            throw new FaqCustomException(FaqExceptionEnum.USER_NOT_EXIST);
        });

        if(!user.getUserRole().equals(UserRole.ADMIN)) {
            throw new FaqCustomException(FaqExceptionEnum.ACCESS_FORBIDDEN);
        }

        BoardEntity faq = faqRepository.findById(faqUpdateRequestDTO.getBoardId()).orElseThrow(() -> {
            throw new FaqCustomException(FaqExceptionEnum.INVALID_FAQ_ID);
        });

        faq.updateBoard(faqUpdateRequestDTO.getBoardTitle(), faqUpdateRequestDTO.getBoardContent());
        Long newFaqId = faqRepository.save(faq).getBoardId();

        return faqDetail(newFaqId);
    }

    // FAQ 삭제
    public List<FaqResponseDTO> faqDelete(final String userId, final Long boardId) {

        UserEntity user = userRepository.findByUserId(userId).orElseThrow(() -> {
            throw new FaqCustomException(FaqExceptionEnum.USER_NOT_EXIST);
        });

        if(!user.getUserRole().equals(UserRole.ADMIN)) {
            throw new FaqCustomException(FaqExceptionEnum.ACCESS_FORBIDDEN);
        }

        faqRepository.deleteById(boardId);

        return faqList();
    }


}
