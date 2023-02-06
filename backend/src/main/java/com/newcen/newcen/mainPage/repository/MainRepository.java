package com.newcen.newcen.mainPage.repository;

import com.newcen.newcen.common.entity.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MainRepository extends JpaRepository<BoardEntity, Long> {
}
