package com.newcen.newcen.question.repository;

import com.newcen.newcen.common.entity.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionsRepository extends JpaRepository<BoardEntity, String> {


}
