package com.newcen.newcen.common.repository;

import com.newcen.newcen.common.entity.BoardFileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardFileRepository extends JpaRepository<BoardFileEntity, String> {
    List<BoardFileEntity> findByBoardId(Long boardId);
}
