package com.newcen.newcen.notice.service;

import com.newcen.newcen.common.dto.request.SearchCondition;
import com.newcen.newcen.common.entity.BoardEntity;
import com.newcen.newcen.common.entity.BoardFileEntity;
import com.newcen.newcen.common.entity.UserEntity;
import com.newcen.newcen.common.entity.UserRole;
import com.newcen.newcen.notice.dto.request.NoticeCreateFileRequestDTO;
import com.newcen.newcen.notice.dto.request.NoticeCreateRequestDTO;
import com.newcen.newcen.notice.dto.request.NoticeUpdateRequestDTO;
import com.newcen.newcen.notice.dto.response.NoticeDetailResponseDTO;
import com.newcen.newcen.notice.dto.response.NoticeListResponseDTO;
import com.newcen.newcen.notice.dto.response.NoticeOneResponseDTO;
import com.newcen.newcen.notice.repository.NoticeFileRepository;
import com.newcen.newcen.notice.repository.NoticeRepository;
import com.newcen.newcen.notice.repository.NoticeRepositorySupport;
import com.newcen.newcen.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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
    private final NoticeFileRepository noticeFileRepository;

    private final NoticeRepositorySupport noticeRepositorySupport;

    // 공지사항 목록 조회
    public NoticeListResponseDTO retrieve() {
        List<BoardEntity> entityList = noticeRepositorySupport.getNoticeList();      // BoardEntity 를 NoticeListResponseDTO 로 변경해서 return 해줘야함
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

    //공지사항 목록 조회 페이지네이션
    public PageImpl<NoticeDetailResponseDTO> getNoticeList(Pageable pageable){
        PageImpl<NoticeDetailResponseDTO> result = noticeRepositorySupport.getNoticeList(pageable);
        return result;
    }
    //공지사항 검색 및 페이지 제네이션
    public PageImpl<NoticeDetailResponseDTO> getPageListWithSearch(SearchCondition searchCondition, Pageable pageable){
        PageImpl<NoticeDetailResponseDTO> result = noticeRepositorySupport.getPageNoticeListWithSearch(searchCondition, pageable);
        return result;
    }

    // 공지사항 한개 조회 (해당 공지사항의 첨부된 파일 list 조회)
    public NoticeOneResponseDTO retrieveOne(final Long boardId) {       // boardId : 공지사항 한개 id

        Optional<BoardEntity> targetEntity = noticeRepository.findById(boardId);
        List<BoardFileEntity> fileEntity = noticeFileRepository.findByBoardId(boardId);     // 해당 공지사항의 첨부된 파일 list 조회

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
            final NoticeUpdateRequestDTO dto,
            final String userId
    ) {
        Optional<BoardEntity> targetEntity = noticeRepository.findById(boardId);    // 수정 target 조회
        Optional<UserEntity> userEntity = userRepository.findById(userId);
        String content =null;
        String title = null;
        if (!userEntity.get().getUserRole().equals(UserRole.ADMIN)) {
            throw new RuntimeException("관리자가 아닙니다.");
        }

        if (targetEntity.isPresent()) {
            if (dto.getBoardContent()==null || dto.getBoardContent().equals("")){
                content = targetEntity.get().getBoardContent();
            }else {
                content = dto.getBoardContent();
            }
            if (dto.getBoardTitle()==null || dto.getBoardTitle().equals("")){
                title = targetEntity.get().getBoardContent();
            }else {
                title = dto.getBoardTitle();
            }
            targetEntity.get().updateBoard(title, content);
            noticeRepository.save(targetEntity.get());
        }

        return retrieveOne(boardId);
    }

    // 공지사항 삭제
    public boolean delete (final Long boardId, final String userId) {
        Optional<UserEntity> userEntity = userRepository.findById(userId);
        if (!userEntity.get().getUserRole().equals(UserRole.ADMIN)) {
            throw new RuntimeException("관리자가 아닙니다.");
        }
        try {
            noticeRepository.deleteById(boardId);
        } catch (Exception e) {
            log.error("삭제할 공지사항이 존재하지 않아 삭제에 실패했습니다. - ID: {}, error: {}", boardId, e.getMessage());   // [서버에 기록할 메세지]
            throw new RuntimeException("삭제할 공지사항이 존재하지 않아 삭제에 실패했습니다.");      // [클라이언트에게 전달할 메세지]
        }
        return true;
    }

    // 공지사항 파일 등록
    public NoticeOneResponseDTO createFile (
            final Long boardId,     // boardId : 파일 추가할 공지사항 id
            final NoticeCreateFileRequestDTO createFileRequestDTO,
            final String userId
    ) {
        UserEntity userEntity = userRepository.findById(userId).get();

        // UserRole 이 ADMIN 이 아닐 경우 exception
        if (!userEntity.getUserRole().equals(UserRole.ADMIN)) {
            throw new RuntimeException("관리자가 아닙니다.");
        }

        // 파일을 추가할 공지사항 entity
        BoardEntity boardEntity = noticeRepository.findById(boardId).get();
        BoardFileEntity boardFile = createFileRequestDTO.toEntity(boardEntity);
        noticeFileRepository.save(boardFile);
        log.info("파일이 저장되었습니다. - 파일 주소: {}", createFileRequestDTO.getBoardFilePath());
        return retrieveOne(boardFile.getBoardId());
    }

    // 공지사항 파일 수정
//    public NoticeOneResponseDTO updateFile (
//            final Long boardId,         // boardId : 수정 대상의 공지사항 id
//            final String boardFileId,   // boardFileId : 수정 대상의 공지사항 파일 id
//            final NoticeUpdateFileRequestDTO updateFileRequestDTO,
//            final String userId
//    ) {
//
//        Optional<BoardEntity> targetBoard = noticeRepository.findById(boardId);     // 수정 대상의 공지사항
//        Optional<BoardFileEntity> targetBoardFile = noticeFileRepository.findById(boardFileId);     // 수정 대상의 공지사항 파일
//        Optional<UserEntity> userEntity = userRepository.findById(userId);
//
//        if (!userEntity.get().getUserRole().equals(UserRole.ADMIN)) {
//            throw new RuntimeException("관리자가 아닙니다.");
//        }
//
//        // 게시물을 작성한 사람과 userId 가 일치해야 함
//        if (!targetBoard.get().getUserId().equals(userId)) {    // 일치하지 않으면
//            throw new RuntimeException("본인이 작성한 글이 아닙니다.");
//        }
//
//        // 파일이 존재하면
//        if (targetBoardFile.isPresent()) {
//
//            // 수정하고자 하는 공지사항 파일이 해당 공지사항에 존재하는 파일이어야 함
//            if (!targetBoard.get().getBoardId().equals(targetBoardFile.get().getBoardId())) {
//                throw new RuntimeException("해당 공지사항의 파일이 아닙니다.");
//            }
//
//            // 생성자로 변경 (BoardFileEntity.java 의 @AllArgsConstructor 이용)
//            BoardFileEntity fileEntity =
//                    new BoardFileEntity(
//                            targetBoardFile.get().getBoardFileId(),
//                            updateFileRequestDTO.getBoardFilePath(),
//                            targetBoard.get().getBoardId());
//
//            noticeFileRepository.save(fileEntity);
//        }
//        return retrieveOne(boardId);
//    }

    // 공지사항 파일 삭제
    public NoticeOneResponseDTO deleteFile (
            final Long boardId,         // boardId : 수정 대상의 공지사항 id
            final String boardFileId,   // boardFileId : 수정 대상의 공지사항 파일 id
            final String userId
    ) {
        Optional<BoardEntity> targetBoard = noticeRepository.findById(boardId);     // 수정 대상의 공지사항
        Optional<BoardFileEntity> targetBoardFile = noticeFileRepository.findById(boardFileId);     // 수정 대상의 공지사항 파일

        Optional<UserEntity> userEntity = userRepository.findById(userId);
        if (!userEntity.get().getUserRole().equals(UserRole.ADMIN)) {
            throw new RuntimeException("관리자가 아닙니다.");
        }

        // 게시물을 작성한 사람과 userId 가 일치해야 함
        if (!targetBoard.get().getUserId().equals(userId)) {    // 일치하지 않으면
            throw new RuntimeException("본인이 작성한 글이 아닙니다.");
        }

        // 삭제하고자 하는 공지사항 파일이 해당 공지사항에 존재하는 파일이어야 함
        if (!targetBoard.get().getBoardId().equals(targetBoardFile.get().getBoardId())) {
            throw new RuntimeException("해당 공지사항의 파일이 아닙니다.");
        }

        try {
            noticeFileRepository.deleteById(boardFileId);
        } catch (Exception e) {
            log.error("삭제할 파일이 존재하지 않아 삭제에 실패했습니다. - fileID: {}, error: {}", boardFileId, e.getMessage());   // [서버에 기록할 메세지]

            throw new RuntimeException("삭제할 파일이 존재하지 않아 삭제에 실패했습니다.");      // [클라이언트에게 전달할 메세지]
        }

        return retrieveOne(boardId);
    }

}
