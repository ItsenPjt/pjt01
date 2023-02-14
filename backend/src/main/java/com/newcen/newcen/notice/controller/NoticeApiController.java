package com.newcen.newcen.notice.controller;

import com.newcen.newcen.comment.dto.request.CommentCreateRequest;
import com.newcen.newcen.comment.dto.request.CommentUpdateRequest;
import com.newcen.newcen.comment.dto.response.CommentListResponseDTO;
import com.newcen.newcen.comment.service.CommentService;
import com.newcen.newcen.commentFile.dto.request.CommentFileCreateRequest;
import com.newcen.newcen.commentFile.dto.request.CommentFileUpdateRequest;
import com.newcen.newcen.commentFile.dto.response.CommentFileListResponseDTO;
import com.newcen.newcen.commentFile.service.CommentFileService;
import com.newcen.newcen.common.dto.request.SearchCondition;
import com.newcen.newcen.common.entity.BoardEntity;
import com.newcen.newcen.common.entity.UserEntity;
import com.newcen.newcen.notice.dto.request.NoticeCreateFileRequestDTO;
import com.newcen.newcen.notice.dto.request.NoticeCreateRequestDTO;
import com.newcen.newcen.notice.dto.request.NoticeUpdateFileRequestDTO;
import com.newcen.newcen.notice.dto.request.NoticeUpdateRequestDTO;
import com.newcen.newcen.notice.dto.response.NoticeDetailResponseDTO;
import com.newcen.newcen.notice.dto.response.NoticeListResponseDTO;
import com.newcen.newcen.notice.dto.response.NoticeOneResponseDTO;
import com.newcen.newcen.notice.repository.NoticeRepository;
import com.newcen.newcen.notice.service.NoticeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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
    private final NoticeRepository noticeRepository;

    private final NoticeService noticeService;
    private final CommentService commentService;

    private final CommentFileService commentFileService;
    // 공지사항 목록 요청 (GET)
    @GetMapping()
    public ResponseEntity<?> listNotice(Pageable pageable) {


        PageImpl<NoticeDetailResponseDTO> responseDTO = noticeService.getNoticeList(pageable);

        return ResponseEntity
                .ok()
                .body(responseDTO);
    }
    @PostMapping("/search")
    public ResponseEntity<?> getPageListNotice(@RequestBody SearchCondition searchCondition, Pageable pageable) {
        log.info("/api/notices GET request");

        PageImpl<NoticeDetailResponseDTO> responseDTO = noticeService.getPageListWithSearch(searchCondition, pageable);

        return ResponseEntity
                .ok()
                .body(responseDTO);
    }

    // 공지사항 상세조회 (GET)
    @GetMapping("/{board_id}")
    public ResponseEntity<?> oneNotice(@PathVariable("board_id") Long boardId) {
        log.info("/api/notices/{} GET request", boardId);

        NoticeOneResponseDTO responseDTO = noticeService.retrieveOne(boardId);

        return ResponseEntity
                .ok()
                .body(responseDTO);
    }

    // 공지사항 작성 (POST)
    @PostMapping
    public ResponseEntity<?> createNotice(
            @AuthenticationPrincipal String userId,
            @Validated @RequestBody NoticeCreateRequestDTO requestDTO,
            BindingResult result
    ) {
        if (result.hasErrors()) {
            log.warn("DTO 검증 에러 발생: {}", result.getFieldError());

            return ResponseEntity
                    .badRequest()
                    .body(result.getFieldError());
        }

        log.info("/api/notices POST request");

        try {
            NoticeOneResponseDTO responseDTO = noticeService.create(requestDTO, userId);

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

    // 공지사항 수정 (PATCH)
    @PatchMapping("/{board_id}")
    public ResponseEntity<?> updateNotice(
            @AuthenticationPrincipal String userId,
            @PathVariable("board_id") Long boardId,
            @Validated @RequestBody NoticeUpdateRequestDTO requestDTO,
            BindingResult result
    ) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest()
                    .body(result.getFieldError());
        }
        log.info("/api/todos/{} PUT request", boardId);
        log.info("modifying dto : {}", requestDTO);

        try {
            NoticeOneResponseDTO responseDTO = noticeService.update(boardId, requestDTO, userId);
            return ResponseEntity
                    .ok()
                    .body(responseDTO);
        } catch (Exception e) {
            return ResponseEntity
                    .internalServerError()
                    .body(NoticeListResponseDTO
                            .builder()
                            .error(e.getMessage()));
        }
    }

    // 공지사항 삭제 (DELETE)
    @DeleteMapping("/{board_id}")
    public ResponseEntity<?> deleteNotice(
            @AuthenticationPrincipal String userId,
            @PathVariable("board_id") Long boardId
    ) {
        log.info("/api/notices/{} DELETE request", boardId);

        if (boardId == null || boardId.equals("")) {
            return ResponseEntity
                    .badRequest()
                    .body(NoticeListResponseDTO.builder()
                            .error("Board ID를 전달해주세요"));
        }
        boolean deleted = noticeService.delete(boardId, userId);
        if (deleted){
            return ResponseEntity.ok().body("게시글이 삭제되었습니다.");
        }else {
            return ResponseEntity.internalServerError().body("게시글 삭제에 실패했습니다.");
        }
    }

    // 공지사항 파일첨부 (POST)
    @PostMapping("/{board_id}/files")
    public ResponseEntity<?> createFileNotice (
            @AuthenticationPrincipal String userId,
            @PathVariable("board_id") Long boardId,
            @Validated @RequestBody NoticeCreateFileRequestDTO requestDTO,
            BindingResult result
    ) {
        if (result.hasErrors()) {
            log.warn("DTO 검증 에러 발생: {}", result.getFieldError());

            return ResponseEntity
                    .badRequest()
                    .body(result.getFieldError());
        }

        log.info("/api/notices/{}/file POST request", boardId);

        try {
            NoticeOneResponseDTO responseDTO = noticeService.createFile(boardId, requestDTO, userId);

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

    // 공지사항 파일 수정 (PATCH)
    @PatchMapping("/{board_id}/files/{board_file_id}")
    public ResponseEntity<?> updateFileNotice(
            @AuthenticationPrincipal String userId,
            @PathVariable("board_id") Long boardId,
            @PathVariable("board_file_id") String boardFileId,
            @Validated @RequestBody NoticeUpdateFileRequestDTO requestDTO,
            BindingResult result
    ) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest()
                    .body(result.getFieldError());
        }

        log.info("/api/todos/{}/file/{} PUT request", boardId, boardFileId);
        log.info("modifying dto : {}", requestDTO);

        try {
            NoticeOneResponseDTO responseDTO = noticeService.updateFile(boardId, boardFileId, requestDTO, userId);
            return ResponseEntity
                    .ok()
                    .body(responseDTO);
        } catch (Exception e) {
            return ResponseEntity
                    .internalServerError()
                    .body(NoticeListResponseDTO
                            .builder()
                            .error(e.getMessage()));
        }
    }

    // 공지사항 파일 삭제 (DELETE)
    @DeleteMapping("/{board_id}/files/{board_file_id}")
    public ResponseEntity<?> deleteFileNotice(
            @AuthenticationPrincipal String userId,
            @PathVariable("board_id") Long boardId,
            @PathVariable("board_file_id") String boardFileId
    ) {
        if (boardId == null || boardId.equals("")) {
            return ResponseEntity
                    .badRequest()
                    .body(NoticeListResponseDTO.builder()
                            .error("Board ID를 전달해주세요"));
        }

        log.info("/api/notices/{}/file/{} DELETE request", boardId, boardFileId);

        try {
            NoticeOneResponseDTO responseDTO = noticeService.deleteFile(boardId, boardFileId, userId);
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

    //공지사항 댓글등록
    @PostMapping("/{boardId}/comments")
    private ResponseEntity<?> createComment(@AuthenticationPrincipal String userId, @Validated @RequestBody CommentCreateRequest dto, @PathVariable("boardId") Long boardId, BindingResult result){
        if (result.hasErrors()){
            log.warn("DTO 검증 에러 발생 : {} ", result.getFieldError());
            return ResponseEntity
                    .badRequest()
                    .body(result.getFieldError());
        }
        BoardEntity board = noticeRepository.findById(boardId).get();
        if (board ==null){
            log.warn("해당 글이 없습니다.");
            return ResponseEntity
                    .badRequest()
                    .body("해당 글이 없습니다.");
        }
//        UserEntity getUser = userRepository.findByUserId(userId).get();
//        if (getUser ==null){
//            log.warn("해당 유저가 없습니다.");
//            return ResponseEntity
//                    .badRequest()
//                    .body("해당 유저가 없습니다.");
//        }
        CommentListResponseDTO retrived = commentService.createComment(dto, userId, boardId);


        return ResponseEntity.ok()
                .body(retrived);
    }
    //공지사항 댓글조회
    @GetMapping("/{boardId}/comments")
    private  ResponseEntity<?> getCommentList(Long boardId){
        CommentListResponseDTO retrived = commentService.retrive(boardId);
        return ResponseEntity.ok()
                .body(retrived);
    }
    //공지사항 댓글수정
    @PatchMapping("/{boardId}/comments/{commentId}")
    private ResponseEntity<?> updateComment(@AuthenticationPrincipal String userId,@Validated @RequestBody CommentUpdateRequest dto,
                                            @PathVariable("boardId") Long boardId, @PathVariable("commentId") Long commentId){
        try {
            commentService.updateComment(dto, userId,boardId,commentId);
            CommentListResponseDTO retried = commentService.retrive(boardId);
            return ResponseEntity.ok()
                    .body(retried);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    //공지사항 댓글삭제
    @DeleteMapping("/{boardId}/comments/{commentId}")
    private ResponseEntity<?> deleteComment(@AuthenticationPrincipal String userId, @PathVariable("boardId") Long boardId, @PathVariable("commentId") Long commentId){
        try {
            commentService.deleteComment(userId,commentId);
            CommentListResponseDTO retried = commentService.retrive(boardId);
            return ResponseEntity.ok()
                    .body(retried);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    //공지사항 댓글 파일 조회
    @GetMapping("/{boardId}/comments/{commentId}/files")
    private ResponseEntity<?> getCommentFileList(@PathVariable("boardId") Long boardId, @PathVariable("commentId") Long commentId){
        CommentFileListResponseDTO commentFileList = commentFileService.retrive(commentId);
        return ResponseEntity.ok().body(commentFileList);
    }
    //공지사항 댓글 파일 등록
    @PostMapping("/{boardId}/comments/{commentId}/files")
    private ResponseEntity<?> createCommentFile(@AuthenticationPrincipal String userId, @PathVariable("boardId") Long boardId, @Validated @RequestBody CommentFileCreateRequest dto, @PathVariable("commentId") Long commentId
            , BindingResult result){
        if (result.hasErrors()){
            log.warn("DTO 검증 에러 발생 : {} ", result.getFieldError());
            return ResponseEntity
                    .badRequest()
                    .body(result.getFieldError());
        }
        try {
            CommentFileListResponseDTO commentFileList = commentFileService.createCommentFile(dto,userId,commentId);
            return ResponseEntity
                    .ok()
                    .body(commentFileList);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity
                    .internalServerError()
                    .body("서버에러입니다.");
        }
    }
    //공지사항 댓글 파일 수정
    @PatchMapping("/{boardId}/comments/{commentId}/files/{commentFileId}")
    public ResponseEntity<?> updateCommentFile(@AuthenticationPrincipal String userId, @PathVariable("boardId") Long boardId, @Validated @RequestBody CommentFileUpdateRequest dto, @PathVariable("commentId") Long commentId
            , @PathVariable("commentFileId") String commentFileId
            , BindingResult result){
        if (result.hasErrors()){
            log.warn("DTO 검증 에러 발생 : {} ", result.getFieldError());
            return ResponseEntity
                    .badRequest()
                    .body(result.getFieldError());
        }
        try {
            CommentFileListResponseDTO commentFileListResponseDTO = commentFileService.updateCommentFile(dto, userId, commentId, commentFileId);
            return ResponseEntity
                    .ok()
                    .body(commentFileListResponseDTO);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity
                    .internalServerError()
                    .body("서버에러입니다.");
        }
    }
    //공지사항 댓글 파일 삭제
    @DeleteMapping("/{boardId}/comments/{commentId}/files/{commentFileId}")
    public ResponseEntity<?> deleteCommentFile(@AuthenticationPrincipal String userId, @PathVariable("boardId") Long boardId,  @PathVariable("commentId") Long commentId
            , @PathVariable("commentFileId") String commentFileId
    ){
        try {
            boolean deleteCommentFile = commentFileService.deleteCommentFile(userId, commentId, commentFileId);
            CommentFileListResponseDTO commentFileList = commentFileService.retrive(commentId);
            if (deleteCommentFile==true){
                return ResponseEntity
                        .ok()
                        .body(commentFileList);
            }else {
                return ResponseEntity
                        .badRequest()
                        .body("삭제에 실패했습니다.");
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity
                    .internalServerError()
                    .body("서버에러입니다.");
        }
    }
}
