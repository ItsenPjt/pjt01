package com.newcen.newcen.common.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class BoardEntity {
    @Id
    @Column(name="board_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="board_type")
    @Enumerated(EnumType.STRING)
    private BoardType boardType;

    @Column(name="board_title",nullable = false)
    private String boardTitle;

    @Column(name="board_writer",nullable = false)
    private String boardWriter;

    @Column(name="board_createdate")
    @CreationTimestamp
    private LocalDateTime createDate;

    @Column(name="board_updatedate")
    @LastModifiedDate
    private LocalDateTime boardUpdatedate;

    @Column(name="board_iscomment")
    @Enumerated(EnumType.STRING)
    private BoardCommentIs boardCommentIs;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name="user_id", insertable = false, updatable = false)
    private UserEntity user;

    @Column(name="user_id")
    private String userId;

}
