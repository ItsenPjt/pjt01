package com.newcen.newcen.question.repository;

import com.newcen.newcen.common.entity.BoardFileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;


public interface QuestionsFileRepository extends JpaRepository<BoardFileEntity, String> {
    @Modifying
    @Transactional
    @Query("delete from BoardFileEntity b where b.boardFileId =:boardFileId")
    void selfDeleteById(@Param("boardFileId") String boardFileId);


}
