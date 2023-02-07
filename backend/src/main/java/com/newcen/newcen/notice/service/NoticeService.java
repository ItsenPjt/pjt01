package com.newcen.newcen.notice.service;

import com.newcen.newcen.common.entity.BoardEntity;
import com.newcen.newcen.common.entity.BoardType;
import com.newcen.newcen.common.entity.UserEntity;
import com.newcen.newcen.common.entity.UserRole;
import com.newcen.newcen.notice.dto.request.NoticeCreateRequestDTO;
import com.newcen.newcen.notice.dto.request.NoticeUpdateRequestDTO;
import com.newcen.newcen.notice.dto.response.NoticeDetailResponseDTO;
import com.newcen.newcen.notice.dto.response.NoticeListResponseDTO;
import com.newcen.newcen.notice.repository.NoticeRepository;
import com.newcen.newcen.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class NoticeService {

    private final UserRepository userRepository;
    private final NoticeRepository noticeRepository;

    // 공지사항 목록 조회
    public NoticeListResponseDTO retrieve() {
        List<BoardEntity> entityList = noticeRepository.findAll();      // BoardEntity 를 NoticeListResponseDTO 로 변경해서 return 해줘야함

        // 1. BoardEntity 를 NoticeDetailResponseDTO 로 우선 변경
        List<NoticeDetailResponseDTO> dtoList = entityList.stream()
                .map(NoticeDetailResponseDTO::new)
                .collect(Collectors.toList());

        return NoticeListResponseDTO
                .builder()
                .notices(dtoList)
                .build();
    }

    // 공지사항 한개 조회
    public NoticeListResponseDTO retrieveOne(final Long id) {

        Optional<BoardEntity> targetEntity = noticeRepository.findById(id);




        return null;
    }

    // 공지사항 등록
    public NoticeListResponseDTO create(final NoticeCreateRequestDTO createRequestDTO, final String userId) {

        UserEntity userEntity = userRepository.findById(userId).get();

        if (!userEntity.getUserRole().equals(UserRole.ADMIN)) {
            throw new RuntimeException("관리자가 아닙니다.");
        }

        BoardEntity board = createRequestDTO.toEntity(userEntity);      // 이 DTO 를 entity 로 변환해서 save 메서드 안에 넣기
        noticeRepository.save(board);

        log.info("공지사항이 저장되었습니다. - 제목: {}, 내용: {}, 댓글 허용 여부: {}",
                createRequestDTO.getBoardTitle(), createRequestDTO.getBoardContent(), createRequestDTO.getBoardCommentIs());

        return retrieve();
    }

    // 공지사항 수정
    public NoticeListResponseDTO update(final Long id, final NoticeUpdateRequestDTO updateRequestDTO) {       // id : 수정 대상의 공지사항 id

        // 수정 target 조회
        Optional<BoardEntity> targetEntity = noticeRepository.findById(id);

        targetEntity.ifPresent(entity -> {
            // entity.setTitle(updateRequestDTO.getBoardTitle());

//            entity(BoardType.NOTICE, updateRequestDTO.getBoardTitle(),
//                    targetEntity.get().getBoardWriter(), updateRequestDTO.getBoardContent(),
//                    updateRequestDTO.getBoardCommentIs(), targetEntity.get().getUserId());




        });

        return retrieve();
    }

    // 공지사항 삭제
    public NoticeListResponseDTO delete (final Long id) {
        try {
            noticeRepository.deleteById(id);
        } catch (Exception e) {
            log.error("id가 존재하지 않아 삭제에 실패했습니다. - ID: {}, error: {}", id, e.getMessage());   // [서버에 기록할 메세지]

            throw new RuntimeException("id가 존재하지 않아 삭제에 실패했습니다.");      // [클라이언트에게 전달할 메세지]
        }
        return retrieve();
    }
}
