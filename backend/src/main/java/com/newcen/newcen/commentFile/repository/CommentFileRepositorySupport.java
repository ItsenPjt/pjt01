package com.newcen.newcen.commentFile.repository;

import com.newcen.newcen.common.entity.CommentFileEntity;
import com.newcen.newcen.common.entity.QCommentFileEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CommentFileRepositorySupport {

    public CommentFileRepositorySupport(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    private final JPAQueryFactory jpaQueryFactory;

    QCommentFileEntity qCommentFileEntity = QCommentFileEntity.commentFileEntity;

    public List<CommentFileEntity> findCommentFileListByCommentId(Long commentId){
        return jpaQueryFactory.select(qCommentFileEntity)
                .from(qCommentFileEntity)
                .where(qCommentFileEntity.commentId.eq(commentId))
                .fetch();
    }
}
