package com.newcen.newcen.notice.repository;

import com.newcen.newcen.common.entity.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<BoardEntity, Long> {
}
