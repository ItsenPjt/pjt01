package com.newcen.newcen.common.repository;

import com.newcen.newcen.common.entity.CommentFileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentFileRepository extends JpaRepository<CommentFileEntity, String> {

    List<CommentFileEntity> findByBoardId(Long BoardId);
}
