package com.newcen.newcen.comment.repository;

import com.newcen.newcen.comment.dto.request.FixedPageRequest;
import com.newcen.newcen.comment.dto.response.CommentListResponseDTO;
import com.newcen.newcen.comment.dto.response.CommentResponseDTO;
import com.newcen.newcen.common.entity.*;
import com.newcen.newcen.notice.dto.response.NoticeDetailResponseDTO;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class CommentRepositorySupport  extends QuerydslRepositorySupport {

    private final JPAQueryFactory jpaQueryFactory;
    public CommentRepositorySupport(JPAQueryFactory jpaQueryFactory) {
        super(CommentEntity.class);
        this.jpaQueryFactory = jpaQueryFactory;

    }
    QCommentEntity qCommentEntity=  QCommentEntity.commentEntity;
    public List<CommentEntity> findAllByBoardId(Long boardId){
        List<CommentEntity> fetch = jpaQueryFactory.select(qCommentEntity)
                .from(qCommentEntity)
                .where(qCommentEntity.boardId.eq(boardId))
                .orderBy(qCommentEntity.commentCreateDate.asc())
                .fetch();
        if (fetch ==null){
            fetch = new ArrayList<>();
            return fetch;
        }else {
            return fetch;
        }

    }
    public PageImpl<CommentResponseDTO> getCommentListPage(Pageable pageable,Long boardId){
        JPQLQuery<CommentEntity> query = jpaQueryFactory
                .select(qCommentEntity)
                .from(qCommentEntity)
                .where(qCommentEntity.boardId.eq(boardId))
                .orderBy(qCommentEntity.commentCreateDate.desc());

        long totalCount = query.fetchCount();
        List<CommentEntity> results = getQuerydsl().applyPagination(pageable,query).fetch();
        List<CommentResponseDTO> dtoList = results.stream()
                .map(CommentResponseDTO::new)
                .collect(Collectors.toList());
        Pageable pageRequest = new FixedPageRequest(pageable, totalCount);
        // 2. NoticeDetailResponseDTO 를 NoticeListResponseDTO 로 변경
        return new PageImpl<>(dtoList, pageRequest, totalCount);

    }

}
