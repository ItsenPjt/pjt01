package com.newcen.newcen.notice.repository;

import com.newcen.newcen.common.entity.BoardFileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoticeFileRepository extends JpaRepository<BoardFileEntity, String> {

    // 특정 공지사항의 파일 목록 조회
    List<BoardFileEntity> findByBoardId(Long boardId);
}
