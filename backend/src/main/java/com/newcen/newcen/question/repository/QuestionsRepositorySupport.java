package com.newcen.newcen.question.repository;

import com.newcen.newcen.common.dto.request.SearchCondition;
import com.newcen.newcen.common.entity.*;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
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

    public List<BoardEntity> getQueestionList(){
        return jpaQueryFactory.select(qBoardEntity)
                .from(qBoardEntity)
                .where(qBoardEntity.boardType.eq(BoardType.QUESTION))
                .fetch();
    };

//    public List<BoardEntity> serch(SearchCondition searchCondition){
//        return jpaQueryFactory.select(qBoardEntity)
//                .where(qBoardEntity.boardTitle)
//                .fetch();
//    }
//
//    private BooleanExpression ratingGoe(String boardTitle) {
//        return boardTitle != null ? qBoardEntity.boardTitle.eq(boardTitle) : null;
//    }
//
//    private BooleanExpression teacherNameEq(String boardContent) {
//        return boardContent != null .hasTe(teacherName) ? lecture.teacherName.eq(teacherName) : null;
//    }
//
//    private BooleanExpression lectureNameEq(String lectureName) {
//        return StringUtils.hasText(lectureName) ? lecture.lectureName.eq(lectureName) : null;
//    }



}
