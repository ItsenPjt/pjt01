package com.newcen.newcen.common.entity;


import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "boardFileId")
@Table(name = "board_file")
public class BoardFileEntity {

    @Id
    @Column(name = "board_file_id")
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String boardFileId;

    @Column(name="board_file_path", nullable = false)
    private String boardFilePath;

    @Column(name="board_id")
    private Long boardId;

    public  void setBoardFilePath(String boardFilePath){
        this.boardFilePath = boardFilePath;
    }



}
