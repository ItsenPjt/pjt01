package com.newcen.newcen.comment.repository;

import com.newcen.newcen.comment.dto.request.FixedPageRequest;
import com.newcen.newcen.comment.dto.response.CommentResponseDTO;
import com.newcen.newcen.common.entity.CommentEntity;
import com.newcen.newcen.common.entity.QCommentEntity;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
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
                .orderBy(qCommentEntity.commentCreateDate.desc())
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
        return new PageImpl<>(dtoList, pageRequest, totalCount);

    }

}
