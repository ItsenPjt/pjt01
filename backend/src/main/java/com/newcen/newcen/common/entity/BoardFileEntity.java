package com.newcen.newcen.common.entity;


import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "boardFileId")
@Table(name = "board_file")
public class BoardFileEntity {

    @Id
    @Column(name="boardFileId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long boardFileId;

    @Column(name="board_file_path", nullable = false)
    private String boardFilePath;
}
