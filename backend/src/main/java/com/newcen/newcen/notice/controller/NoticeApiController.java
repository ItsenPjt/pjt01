package com.newcen.newcen.notice.controller;

import com.newcen.newcen.common.entity.BoardEntity;
import com.newcen.newcen.notice.dto.request.NoticeCreateRequestDTO;
import com.newcen.newcen.notice.dto.response.NoticeListResponseDTO;
import com.newcen.newcen.notice.service.NoticeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController     // controller + @ResponseBody --> JSON/XML 형태로 객체 데이터 반환 목적
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/notices")
public class NoticeApiController {

    private final NoticeService noticeService;

    // 공지사항 목록 요청 (GET)
    @GetMapping()
    public ResponseEntity<?> listNotice() {
        log.info("/api/notices GET request");

        NoticeListResponseDTO responseDTO = noticeService.retrieve();

        return ResponseEntity
                .ok()
                .body(responseDTO);
    }

    // 공지사항 상세조회 (GET)



    // 공지사항 작성 (POST)
    @PostMapping
    public ResponseEntity<?> createNotice(
            @AuthenticationPrincipal String userId,         // -> 확인 필요
            @Validated @RequestBody NoticeCreateRequestDTO requestDTO,
            BindingResult result
    ) {
        log.info("/api/notices POST request");

        if (result.hasErrors()) {
            log.warn("DTO 검증 에러 발생: {}", result.getFieldError());

            return ResponseEntity
                    .badRequest()
                    .body(result.getFieldError());
        }

        try {
            NoticeListResponseDTO responseDTO = noticeService.create(requestDTO, userId);
            return ResponseEntity
                    .ok()
                    .body(responseDTO);
        } catch (RuntimeException e) {
            log.error(e.getMessage());
            return ResponseEntity
                    .internalServerError()
                    .body(NoticeListResponseDTO
                            .builder()
                            .error(e.getMessage()));
        }
    }

    // 공지사항 수정 (PUT)



    // 공지사항 삭제 (DELETE)
    @DeleteMapping("/{board_id}")
    public ResponseEntity<?> deleteNotice(@PathVariable("board_id") Long boardId) {
        log.info("/api/notices/{} DELETE request", boardId);

        if (boardId == null || boardId.equals("")) {
            return ResponseEntity
                    .badRequest()
                    .body(NoticeListResponseDTO.builder()
                            .error("Board ID를 전달해주세요"));
        }

        try {
            NoticeListResponseDTO responseDTO = noticeService.delete(boardId);
            return ResponseEntity
                    .ok()
                    .body(responseDTO);
        } catch (Exception e) {
            log.error(e.getMessage());

            return ResponseEntity
                    .internalServerError()
                    .body(NoticeListResponseDTO
                            .builder()
                            .error(e.getMessage()));
        }
    }

    //
}
