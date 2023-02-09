package com.newcen.newcen.notice.service;

import com.newcen.newcen.common.entity.*;
import com.newcen.newcen.notice.dto.request.NoticeCreateRequestDTO;
import com.newcen.newcen.notice.dto.request.NoticeUpdateRequestDTO;
import com.newcen.newcen.notice.dto.response.NoticeDetailResponseDTO;
import com.newcen.newcen.notice.dto.response.NoticeListResponseDTO;
import com.newcen.newcen.notice.dto.response.NoticeOneResponseDTO;
import com.newcen.newcen.notice.repository.NoticeFileRepository;
import com.newcen.newcen.notice.repository.NoticeRepository;
import com.newcen.newcen.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class NoticeService {

    private final UserRepository userRepository;
    private final NoticeRepository noticeRepository;
    private final NoticeFileRepository noticeFileRepository;

    // 공지사항 목록 조회
    public NoticeListResponseDTO retrieve() {
        List<BoardEntity> entityList = noticeRepository.findAll();      // BoardEntity 를 NoticeListResponseDTO 로 변경해서 return 해줘야함

        // 1. BoardEntity 를 NoticeDetailResponseDTO 로 우선 변경
        List<NoticeDetailResponseDTO> dtoList = entityList.stream()
                .map(NoticeDetailResponseDTO::new)
                .collect(Collectors.toList());

        // 2. NoticeDetailResponseDTO 를 NoticeListResponseDTO 로 변경
        return NoticeListResponseDTO
                .builder()
                .notices(dtoList)
                .build();
    }

    // 공지사항 한개 조회 (해당 공지사항의 첨부된 파일 모두 조회)
    public NoticeOneResponseDTO retrieveOne(final Long boardId) {       // boardId : 공지사항 한개 id

        Optional<BoardEntity> targetEntity = noticeRepository.findById(boardId);
        List<BoardFileEntity> fileEntity = noticeFileRepository.findAll();

        List<NoticeDetailResponseDTO> dtoList = targetEntity.stream()
                .map(NoticeDetailResponseDTO::new)
                .collect(Collectors.toList());

        return NoticeOneResponseDTO
                .builder()
                .noticeDetails(dtoList)
                .boardFileEntityList(fileEntity)        // file list
                .build();
    }

    // 공지사항 등록
    public NoticeOneResponseDTO create (
            final NoticeCreateRequestDTO createRequestDTO,
            final String userId
    ) throws RuntimeException {

        UserEntity userEntity = userRepository.findById(userId).get();

        // UserRole 이 ADMIN 이 아닐 경우 exception
        if (!userEntity.getUserRole().equals(UserRole.ADMIN)) {
            throw new RuntimeException("관리자가 아닙니다.");
        }

        BoardEntity board = createRequestDTO.toEntity(userEntity);      // 이 DTO 를 entity 로 변환해서 save 메서드 안에 넣기
        board.setUserId(userId);       // user 정보 직접 추가

        noticeRepository.save(board);

        log.info("공지사항이 저장되었습니다. - 제목: {}, 내용: {}, 댓글 허용 여부: {}",
                createRequestDTO.getBoardTitle(), createRequestDTO.getBoardContent(), createRequestDTO.getBoardCommentIs());

        return retrieveOne(board.getBoardId());
    }

    // 공지사항 수정
    public NoticeOneResponseDTO update (
            final Long boardId,     // boardId : 수정 대상의 공지사항 id
            final NoticeUpdateRequestDTO updateRequestDTO,
            final String userId
    ) {
        Optional<BoardEntity> targetEntity = noticeRepository.findById(boardId);    // 수정 target 조회
        Optional<UserEntity> userEntity = userRepository.findById(userId);

        if (!userEntity.get().getUserRole().equals(UserRole.ADMIN)) {
            throw new RuntimeException("관리자가 아닙니다.");
        }

        if (targetEntity.isPresent()) {
            BoardEntity boardEntity =
                    new BoardEntity(targetEntity.get().getBoardId(),
                                    BoardType.NOTICE,
                                    updateRequestDTO.getBoardTitle(),
                                    userEntity.get().getUserName(),
                                    updateRequestDTO.getBoardContent(),
                                    targetEntity.get().getCreateDate(),
                                    LocalDateTime.now(),
                                    updateRequestDTO.getBoardCommentIs(),
                                    userEntity.get().getUserId());

            noticeRepository.save(boardEntity);
        }

        return retrieveOne(boardId);
    }

    // 공지사항 삭제
    public NoticeListResponseDTO delete (final Long boardId, final String userId) {

        Optional<UserEntity> userEntity = userRepository.findById(userId);
        if (!userEntity.get().getUserRole().equals(UserRole.ADMIN)) {
            throw new RuntimeException("관리자가 아닙니다.");
        }

        try {
            noticeRepository.deleteById(boardId);
        } catch (Exception e) {
            log.error("id가 존재하지 않아 삭제에 실패했습니다. - ID: {}, error: {}", boardId, e.getMessage());   // [서버에 기록할 메세지]

            throw new RuntimeException("id가 존재하지 않아 삭제에 실패했습니다.");      // [클라이언트에게 전달할 메세지]
        }
        return retrieve();      // 공지사항 목록으로
    }

    // 공지사항 파일 등록

    // 공지사항 파일 수정

    // 공지사항 파일 삭제
}
