package com.newcen.newcen.notice.repository;

import com.newcen.newcen.common.entity.BoardEntity;
import com.newcen.newcen.common.entity.BoardType;
import com.newcen.newcen.common.entity.QBoardEntity;
import com.newcen.newcen.common.entity.QBoardFileEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class NoticeRepositorySupport {

    private final JPAQueryFactory jpaQueryFactory;
    QBoardEntity qBoardEntity = QBoardEntity.boardEntity;
    public List<BoardEntity> getNoticeList(){
        return jpaQueryFactory.select(qBoardEntity)
                .from(qBoardEntity)
            .where(qBoardEntity.boardType.eq(BoardType.NOTICE))
                .fetch();
    };
}
