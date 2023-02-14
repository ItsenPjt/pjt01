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
    private String commentFileId;

    @Column(name="comment_file_path", nullable = false)
    private String commentFilePath;

    @Column(name="comment_id")
    private Long commentId;

    @Column(name="comment_file_user_email")
    private String userEmail;

    public void updatePath(String commentFilePath){
        this.commentFilePath = commentFilePath;
    }
}
