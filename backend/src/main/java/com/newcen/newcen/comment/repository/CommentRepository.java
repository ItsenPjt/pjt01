package com.newcen.newcen.comment.repository;

import com.newcen.newcen.common.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
}
