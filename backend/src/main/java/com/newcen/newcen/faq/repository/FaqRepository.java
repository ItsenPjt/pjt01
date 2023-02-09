package com.newcen.newcen.faq.repository;

import com.newcen.newcen.common.entity.BoardEntity;
import com.newcen.newcen.common.entity.BoardType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FaqRepository extends JpaRepository<BoardEntity, Long> {

    List<BoardEntity> findByBoardType(String type);


}
