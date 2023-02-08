package com.newcen.newcen.question.repository;

import com.newcen.newcen.common.entity.BoardEntity;
import com.newcen.newcen.common.entity.BoardFileEntity;
import com.newcen.newcen.common.entity.QBoardEntity;
import com.newcen.newcen.common.entity.QBoardFileEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class QuestionsRepositorySupport {

    private final JPAQueryFactory jpaQueryFactory;

    QBoardEntity qBoardEntity = QBoardEntity.boardEntity;
    QBoardFileEntity qBoardFileEntity = QBoardFileEntity.boardFileEntity;
    public BoardEntity findBoardByUserIdAndBoardId(String userId, Long boardId){
        return jpaQueryFactory.select(qBoardEntity)
                .from(qBoardEntity).where(qBoardEntity.boardId.eq(boardId))
                .where(qBoardEntity.userId.eq(userId)).fetchOne();
    }


}
