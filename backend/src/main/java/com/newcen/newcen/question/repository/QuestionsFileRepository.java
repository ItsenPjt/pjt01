package com.newcen.newcen.question.repository;

import com.newcen.newcen.common.entity.BoardFileEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionsFileRepository extends JpaRepository<BoardFileEntity, String> {



}
