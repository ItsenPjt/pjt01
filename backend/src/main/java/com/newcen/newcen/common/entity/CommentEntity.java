package com.newcen.newcen.common.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "commentId")
@Table(name = "comment")
public class CommentEntity {

    @Id
    @Column(name="comment_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long commentId;

    @Column(name="comment_content", nullable = false)
    private String commentContent;

    @Column(name="comment_writer", nullable = false)
    private String commentWriter;

    @Column(name="comment_createdate")
    @CreationTimestamp
    private LocalDateTime commentCreateDate;

    @Column(name="comment_updatedate")
    @UpdateTimestamp
    private LocalDateTime commentUpdateDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name="board_id", insertable = false, updatable = false)
    private BoardEntity board;

    @Column(name="board_id")
    private Long boardId;

    @JoinColumn(name="comment_file_id")
    @OneToMany(orphanRemoval = true)
    private final List<CommentFileEntity> commentFileList = new ArrayList<>();

    @JoinColumn(name = "comment_reply_id")
    @OneToMany(orphanRemoval = true)
    private final List<CommentReplyEntity> commentReplyList = new ArrayList<>();

}
