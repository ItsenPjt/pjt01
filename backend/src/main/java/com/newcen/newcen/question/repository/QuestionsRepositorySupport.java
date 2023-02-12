package com.newcen.newcen.question.repository;

import com.newcen.newcen.common.dto.request.SearchCondition;
import com.newcen.newcen.common.entity.BoardEntity;
import com.newcen.newcen.common.entity.BoardFileEntity;
import com.newcen.newcen.common.entity.QBoardEntity;
import com.newcen.newcen.common.entity.QBoardFileEntity;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;
import java.util.function.Supplier;

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

    public List<BoardEntity> serch(SearchCondition searchCondition){
        return jpaQueryFactory.select(qBoardEntity)
                .fetch();
    }

    BooleanBuilder nullSafeBuilder(Supplier<BooleanExpression> f) {
        try {
            return new BooleanBuilder(f.get());
        } catch (Exception e) {
            return new BooleanBuilder();
        }
    }

    BooleanBuilder userEq(String content) {
        return nullSafeBuilder(() -> qBoardEntity.boardWriter.eq(content));
    }
    BooleanBuilder titleCt(String content) {
        return nullSafeBuilder(() -> qBoardEntity.boardTitle.contains(content));
    }
    BooleanBuilder contentCt(String content) {
        return nullSafeBuilder(() -> qBoardEntity.boardContent.contains(content));
    }

//    BooleanBuilder isSearchable(SearchType sType, String content) {
//        if (sType == SearchType.TIT) {
//            return titleCt(content);
//        }
//        else if(sType == SearchType.STUD) {
//            return userEq(content);
//        }
//        else {
//            return titleCt(content).or(contentCt(content));
//        }
//    }


}
