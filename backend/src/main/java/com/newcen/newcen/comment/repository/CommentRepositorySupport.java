package com.newcen.newcen.comment.repository;

import com.newcen.newcen.common.entity.CommentEntity;
import com.newcen.newcen.common.entity.CommentFileEntity;
import com.newcen.newcen.common.entity.QCommentEntity;
import com.newcen.newcen.common.entity.QCommentFileEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CommentRepositorySupport {

    private final JPAQueryFactory jpaQueryFactory;

    QCommentEntity qCommentEntity=  QCommentEntity.commentEntity;
    public List<CommentEntity> findAllByBoardId(Long boardId){
        List<CommentEntity> fetch = jpaQueryFactory.select(qCommentEntity)
                .from(qCommentEntity)
                .where(qCommentEntity.boardId.eq(boardId))
                .fetch();
        if (fetch ==null){
            fetch = new ArrayList<>();
            return fetch;
        }else {
            return fetch;
        }

    }

}
