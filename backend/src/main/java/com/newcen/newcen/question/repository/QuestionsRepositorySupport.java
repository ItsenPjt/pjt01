package com.newcen.newcen.question.repository;

import com.newcen.newcen.comment.dto.request.FixedPageRequest;
import com.newcen.newcen.common.dto.request.SearchCondition;
import com.newcen.newcen.common.entity.*;
import com.newcen.newcen.notice.dto.response.NoticeDetailResponseDTO;
import com.newcen.newcen.question.response.QuestionResponseDTO;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;


@Repository
public class QuestionsRepositorySupport extends QuerydslRepositorySupport {

    private final JPAQueryFactory jpaQueryFactory;

    public QuestionsRepositorySupport(JPAQueryFactory jpaQueryFactory) {
        super(BoardEntity.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    QBoardEntity qBoardEntity = QBoardEntity.boardEntity;
    QBoardFileEntity qBoardFileEntity = QBoardFileEntity.boardFileEntity;
    public BoardEntity findBoardByUserIdAndBoardId(String userId, Long boardId){
        return jpaQueryFactory.select(qBoardEntity)
                .from(qBoardEntity).where(qBoardEntity.boardId.eq(boardId))
                .where(qBoardEntity.userId.eq(userId)).fetchOne();
    }

    public List<BoardEntity> getQuestionList(){
        return jpaQueryFactory.select(qBoardEntity)
                .from(qBoardEntity)
                .where(qBoardEntity.boardType.eq(BoardType.QUESTION))
                .fetch();
    };

    //목록조회
    public PageImpl<QuestionResponseDTO> getQuestionListPage(Pageable pageable){
        JPQLQuery<BoardEntity> query = jpaQueryFactory
                .select(qBoardEntity)
                .from(qBoardEntity)
                .where(qBoardEntity.boardType.eq(BoardType.QUESTION))
                .orderBy(qBoardEntity.createDate.desc());
        long totalCount = query.fetchCount();
        List<BoardEntity> results = getQuerydsl().applyPagination(pageable,query).fetch();
        List<QuestionResponseDTO> dtoList = results.stream()
                .map(QuestionResponseDTO::new)
                .collect(Collectors.toList());
        Pageable pageRequest = new FixedPageRequest(pageable, totalCount);
        // 2. NoticeDetailResponseDTO 를 NoticeListResponseDTO 로 변경
        return new PageImpl<>(dtoList, pageRequest, totalCount);

    }
    public PageImpl<QuestionResponseDTO> getQuestionListPageWithSearch(SearchCondition searchCondition, Pageable pageable){
        JPQLQuery<BoardEntity> query = jpaQueryFactory
                .select(qBoardEntity)
                .from(qBoardEntity)
                .where(qBoardEntity.boardType.eq(BoardType.QUESTION)
                        ,ContentMessageTitleEq(searchCondition.getBoardContent(),searchCondition.getBoardTitle())
                        ,boardWriterEq(searchCondition.getBoardWriter()))
                .orderBy(qBoardEntity.createDate.desc());
        long totalCount = query.fetchCount();
        List<BoardEntity> results = getQuerydsl().applyPagination(pageable,query).fetch();
        List<QuestionResponseDTO> dtoList = results.stream()
                .map(QuestionResponseDTO::new)
                .collect(Collectors.toList());
        Pageable pageRequest = new FixedPageRequest(pageable, totalCount);
        return new PageImpl<>(dtoList, pageRequest, totalCount);

    }
    private BooleanExpression ContentMessageTitleEq(String boardContent,String boardTitle){

        if(!boardContent.isEmpty() && !boardTitle.isEmpty()){
            return qBoardEntity.boardTitle.contains(boardTitle).or(qBoardEntity.boardContent.contains(boardContent));
        }
        if(!boardContent.isEmpty() && boardTitle.isEmpty()){
            return qBoardEntity.boardContent.contains(boardContent);
        }
        if(boardContent.isEmpty() && !boardTitle.isEmpty()){
            return qBoardEntity.boardTitle.contains(boardTitle);
        }
        return null;
    }
    private BooleanExpression boardTitleEq(String boardTitle){
        if(boardTitle == null || boardTitle.isEmpty()){
            return null;
        }
        return qBoardEntity.boardTitle.contains(boardTitle);
    }

    private BooleanExpression boardContentEq(String boardContent){
        if(boardContent == null || boardContent.isEmpty()){
            return null;
        }
        return qBoardEntity.boardContent.contains(boardContent);
    }

    private BooleanExpression boardWriterEq(String boardWriter){
        if(boardWriter == null || boardWriter.isEmpty()){
            return null;
        }
        return qBoardEntity.boardWriter.contains(boardWriter);
    }

}
