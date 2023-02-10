package com.newcen.newcen.common.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "commentReplyId")
@Table(name = "comment_reply")
public class CommentReplyEntity {

    @Id
    @Column(name = "comment_reply_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentReplyId;

    @Column(name = "comment_reply_content", nullable = false)
    private String commentReplyContent;

    @Column(name = "comment_reply_writer", nullable = false)
    private String commentReplyWriter;

    @Column(name = "comment_reply_createdate")
    @CreationTimestamp
    private LocalDateTime commentReplyCreateDate;

    @Column(name = "comment_reply_updatedate")
    @UpdateTimestamp
    private LocalDateTime commentReplyUpdateDate;

    @Column(name="user_id")
    private String userId;

}
