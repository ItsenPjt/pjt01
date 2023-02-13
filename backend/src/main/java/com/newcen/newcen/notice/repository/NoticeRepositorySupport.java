package com.newcen.newcen.notice.repository;

import com.newcen.newcen.comment.dto.request.FixedPageRequest;
import com.newcen.newcen.common.dto.request.SearchCondition;
import com.newcen.newcen.common.entity.BoardEntity;
import com.newcen.newcen.common.entity.BoardType;
import com.newcen.newcen.common.entity.QBoardEntity;
import com.newcen.newcen.notice.dto.response.NoticeDetailResponseDTO;
import com.newcen.newcen.notice.dto.response.NoticeListResponseDTO;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class NoticeRepositorySupport  extends QuerydslRepositorySupport {

    private final JPAQueryFactory jpaQueryFactory;
    QBoardEntity qBoardEntity = QBoardEntity.boardEntity;
    private final EntityManager entityManager;

    public NoticeRepositorySupport(JPAQueryFactory jpaQueryFactory,EntityManager entityManager) {
        super(BoardEntity.class);
        this.jpaQueryFactory = jpaQueryFactory;
        this.entityManager = entityManager;
    }


    public List<BoardEntity>  getNoticeList(){
        return jpaQueryFactory.select(qBoardEntity)
                .from(qBoardEntity)
                .where(qBoardEntity.boardType.eq(BoardType.NOTICE))
                .fetch();
    }
    //목록조회
    public PageImpl<NoticeDetailResponseDTO> getNoticeList(Pageable pageable){
        JPQLQuery<BoardEntity> query = jpaQueryFactory
                .select(qBoardEntity)
                .from(qBoardEntity)
                .where(qBoardEntity.boardType.eq(BoardType.NOTICE))
                .orderBy(qBoardEntity.createDate.desc());
        long totalCount = query.fetchCount();
        List<BoardEntity> results = getQuerydsl().applyPagination(pageable,query).fetch();
        List<NoticeDetailResponseDTO> dtoList = results.stream()
                .map(NoticeDetailResponseDTO::new)
                .collect(Collectors.toList());
        Pageable pageRequest = new FixedPageRequest(pageable, totalCount);
        // 2. NoticeDetailResponseDTO 를 NoticeListResponseDTO 로 변경
        return new PageImpl<>(dtoList, pageRequest, totalCount);

    }
    //목록 검색 및 조회
    public PageImpl<NoticeDetailResponseDTO> getPageNoticeListWithSearch(SearchCondition searchCondition,Pageable pageable){
        JPQLQuery<BoardEntity> query = jpaQueryFactory
                .select(qBoardEntity)
                .from(qBoardEntity)
                .where(qBoardEntity.boardType.eq(BoardType.NOTICE)
                ,boardTitleEq(searchCondition.getBoardTitle())
                ,boardContentEq(searchCondition.getBoardContent())
                ,boardWriterEq(searchCondition.getBoardWriter()))
                .orderBy(qBoardEntity.createDate.desc());
        long totalCount = query.fetchCount();
        List<BoardEntity> results = getQuerydsl().applyPagination(pageable,query).fetch();
        System.out.println(pageable);
        List<NoticeDetailResponseDTO> dtoList = results.stream()
                .map(NoticeDetailResponseDTO::new)
                .collect(Collectors.toList());
        Pageable pageRequest = new FixedPageRequest(pageable, totalCount);
        // 2. NoticeDetailResponseDTO 를 NoticeListResponseDTO 로 변경

        return new PageImpl<>(dtoList, pageRequest, totalCount);

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
