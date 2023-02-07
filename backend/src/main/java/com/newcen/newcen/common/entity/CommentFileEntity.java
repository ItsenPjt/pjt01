package com.newcen.newcen.common.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "commentFileId")
@Table(name = "comment_file")
public class CommentFileEntity {

    @Id
    @Column(name="comment_file_id")
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private long commentFileId;

    @Column(name="comment_file_path", nullable = false)
    private String commentFilePath;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JsonIgnore
//    @JoinColumn(name="comment_id", insertable = false, updatable = false)
//    private CommentEntity comment;
}
