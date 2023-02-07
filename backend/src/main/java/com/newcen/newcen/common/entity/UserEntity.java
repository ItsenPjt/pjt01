package com.newcen.newcen.common.entity;


import lombok.*;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of="userId")
@Table(name = "user")
@DynamicInsert  // insert문 실행 시 null값 컬럼 제외 @ColumnDefault() 사용
@DynamicUpdate  // update문 실행 시 null값 컬럼 제외 @ColumnDefault() 사용
public class UserEntity {


    @Id
    @Column(name = "user_id")
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String userId;

    @Column(name="user_email", nullable = false)
    private String userEmail;
    @Column(name="user_password", nullable = false)
    private String userPassword;

    @Column(name="user_name", nullable = false)
    private String userName;

    @Column(name="user_role")
    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    @CreationTimestamp
    @Column(name="user_regdate")
    private LocalDateTime userRegdate;

    @OneToMany(orphanRemoval = true, mappedBy = "user")
    private final List<BoardEntity> boardList = new ArrayList<>();




}
