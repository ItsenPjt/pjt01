package com.newcen.newcen.faq.controller;

import com.newcen.newcen.common.dto.request.SearchCondition;
import com.newcen.newcen.faq.dto.request.FaqSaveRequestDTO;
import com.newcen.newcen.faq.dto.request.FaqUpdateRequestDTO;
import com.newcen.newcen.faq.dto.response.FaqDetailResponseDTO;
import com.newcen.newcen.faq.dto.response.FaqResponseDTO;
import com.newcen.newcen.faq.service.FaqService;
import com.newcen.newcen.notice.dto.response.NoticeDetailResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class FaqController {

    private final FaqService faqService;

    // FAQ 목록 조회 페이지
    @GetMapping("/api/faqs")
    public ResponseEntity<?> getFaqListPage(Pageable pageable) {

        PageImpl<FaqDetailResponseDTO> faqList = faqService.getFaqListPage(pageable);

        return ResponseEntity
                .ok()
                .body(faqList);
    }

    // FAQ 목록 검색
    @PostMapping("/api/faqs/search")
    public ResponseEntity<?> getFaqListPageWithSearch(@RequestBody SearchCondition searchCondition, Pageable pageable) {
        log.info("/api/notices GET request");
        PageImpl<FaqDetailResponseDTO> responseDTO;
        if (searchCondition.getBoardContent() == null && searchCondition.getBoardTitle() ==null && searchCondition.getBoardWriter()==null){
            responseDTO = faqService.getFaqListPage(pageable);
        }
        else {
            responseDTO = faqService.getFaqListPageWithSearch(searchCondition, pageable);

        }
        return ResponseEntity
                .ok()
                .body(responseDTO);
    }
    // FAQ 상세 조회
    @GetMapping("/api/faqs/{boardId}")
    public ResponseEntity<?> faqDetail(@PathVariable("boardId") Long boardId) {

        FaqDetailResponseDTO faqDetail = faqService.faqDetail(boardId);
        return ResponseEntity
                .ok()
                .body(faqDetail);
    }

    // FAQ 등록
    @PostMapping("/api/faqs")
    public ResponseEntity<?> faqSave(@AuthenticationPrincipal String userId,
                                     @Validated @RequestBody FaqSaveRequestDTO requestDTO) {

        boolean faqList = faqService.faqSave(userId, requestDTO);
        return ResponseEntity
                .ok()
                .body(faqList);
    }

    // FAQ 수정
    @PatchMapping("/api/faqs/{boardId}")
    public ResponseEntity<?> faqUpdate(@AuthenticationPrincipal String userId,
                                       @PathVariable("boardId") Long boardId,
                                       @Validated @RequestBody FaqUpdateRequestDTO requestDTO) {

        requestDTO.setBoardId(boardId);
        boolean faqDetail = faqService.faqUpdate(userId, requestDTO);
        return ResponseEntity
                .ok()
                .body(faqDetail);
    }

    // FAQ 삭제
    @DeleteMapping("/api/faqs/{boardId}")
    public ResponseEntity<?> faqDelete(@AuthenticationPrincipal String userId,
                                       @PathVariable("boardId") Long boardId) {

        boolean faqList = faqService.faqDelete(userId, boardId);
        return ResponseEntity
                .ok()
                .body(faqList);
    }


}
