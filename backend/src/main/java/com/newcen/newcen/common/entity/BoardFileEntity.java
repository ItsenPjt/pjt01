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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        BoardFileEntity boardFile = (BoardFileEntity) o;
        return boardFileId != null && Objects.equals(boardFileId, boardFile.boardFileId);
    }


//    public BoardFileEntity() {
//        this.boardFileId = UUID.randomUUID().toString();
//    }
}
