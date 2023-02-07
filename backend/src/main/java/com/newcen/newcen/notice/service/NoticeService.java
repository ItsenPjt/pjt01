package com.newcen.newcen.notice.service;

import com.newcen.newcen.common.entity.BoardEntity;
import com.newcen.newcen.notice.dto.request.NoticeCreateRequestDTO;
import com.newcen.newcen.notice.dto.response.NoticeDetailResponseDTO;
import com.newcen.newcen.notice.dto.response.NoticeListResponseDTO;
import com.newcen.newcen.notice.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class NoticeService {

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

    // 공지사항 등록
    public NoticeListResponseDTO create( final NoticeCreateRequestDTO createRequestDTO ) {
        noticeRepository.save(createRequestDTO.toEntity());     // 이 DTO 를 entity 로 변환해서 save 메서드 안에 넣기

        log.info("공지사항이 저장되었습니다. 제목: {}, 내용: {}, 댓글 허용 여부: {}",
                createRequestDTO.getBoardTitle(), createRequestDTO.getBoardContent(), createRequestDTO.getBoardCommentIs());

        return retrieve();
    }
}
