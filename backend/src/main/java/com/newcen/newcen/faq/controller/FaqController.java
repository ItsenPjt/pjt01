package com.newcen.newcen.faq.controller;

import com.newcen.newcen.faq.dto.request.FaqSaveRequestDTO;
import com.newcen.newcen.faq.dto.request.FaqUpdateRequestDTO;
import com.newcen.newcen.faq.dto.response.FaqDetailResponseDTO;
import com.newcen.newcen.faq.dto.response.FaqResponseDTO;
import com.newcen.newcen.faq.service.FaqService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    // FAQ 목록 조회
    @GetMapping("/api/faqs")
    public ResponseEntity<?> faqList() {

        List<FaqResponseDTO> faqList = faqService.faqList();
        return ResponseEntity
                .ok()
                .body(faqList);
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

        List<FaqResponseDTO> faqList = faqService.faqSave(userId, requestDTO);
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
        FaqDetailResponseDTO faqDetail = faqService.faqUpdate(userId, requestDTO);

        return ResponseEntity
                .ok()
                .body(faqDetail);
    }

    // FAQ 삭제
    @DeleteMapping("/api/faqs/{boardId}")
    public ResponseEntity<?> faqDelete(@AuthenticationPrincipal String userId,
                                       @PathVariable("boardId") Long boardId) {

        List<FaqResponseDTO> faqList = faqService.faqDelete(userId, boardId);
        return ResponseEntity
                .ok()
                .body(faqList);
    }


}
