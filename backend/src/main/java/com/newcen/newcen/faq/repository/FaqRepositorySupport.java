package com.newcen.newcen.faq.repository;

import com.newcen.newcen.comment.dto.request.FixedPageRequest;
import com.newcen.newcen.common.dto.request.SearchCondition;
import com.newcen.newcen.common.entity.BoardEntity;
import com.newcen.newcen.common.entity.BoardType;
import com.newcen.newcen.common.entity.QBoardEntity;
import com.newcen.newcen.faq.dto.response.FaqDetailResponseDTO;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class FaqRepositorySupport extends QuerydslRepositorySupport {

    public FaqRepositorySupport(JPAQueryFactory jpaQueryFactory) {
        super(BoardEntity.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }
    QBoardEntity qBoardEntity = QBoardEntity.boardEntity;

    private final JPAQueryFactory jpaQueryFactory;

    //faq 목록 조회
    public PageImpl<FaqDetailResponseDTO> getFaqListPage(Pageable pageable){
        JPQLQuery<BoardEntity> query = jpaQueryFactory.select(qBoardEntity)
                .from(qBoardEntity).where(qBoardEntity.boardType.eq(BoardType.FAQ))
                .orderBy(qBoardEntity.createDate.desc());

        long totalCount = query.fetchCount();
        List<BoardEntity> results = getQuerydsl().applyPagination(pageable,query).fetch();
        List<FaqDetailResponseDTO> dtoList = results.stream()
                .map(FaqDetailResponseDTO::new)
                .collect(Collectors.toList());
        Pageable pageRequest = new FixedPageRequest(pageable, totalCount);
        // 2. NoticeDetailResponseDTO 를 NoticeListResponseDTO 로 변경
        return new PageImpl<>(dtoList, pageRequest, totalCount);
    }
    //faq 목록 검색
    public PageImpl<FaqDetailResponseDTO> getPageNoticeListWithSearch(SearchCondition searchCondition, Pageable pageable){
        JPQLQuery<BoardEntity> query = jpaQueryFactory
                .select(qBoardEntity)
                .from(qBoardEntity)
                .where(qBoardEntity.boardType.eq(BoardType.FAQ)
//                        ,boardTitleEq(searchCondition.getBoardTitle())
//                        ,boardContentEq(searchCondition.getBoardContent())
                        ,ContentMessageTitleEq(searchCondition.getBoardContent(), searchCondition.getBoardTitle())
                        ,boardWriterEq(searchCondition.getBoardWriter()))
                .orderBy(qBoardEntity.createDate.desc());
        long totalCount = query.fetchCount();
        List<BoardEntity> results = getQuerydsl().applyPagination(pageable,query).fetch();
        List<FaqDetailResponseDTO> dtoList = results.stream()
                .map(FaqDetailResponseDTO::new)
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
