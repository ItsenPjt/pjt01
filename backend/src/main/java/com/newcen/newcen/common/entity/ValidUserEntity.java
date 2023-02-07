package com.newcen.newcen.common.entity;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Email;

@Entity
//@Setter
@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of="validUserId")
@Table(name = "valid_user")
@DynamicInsert  // insert문 실행 시 null값 컬럼 제외 @ColumnDefault() 사용
@DynamicUpdate  // update문 실행 시 null값 컬럼 제외 @ColumnDefault() 사용
public class ValidUserEntity {

    @Id
    @Column(name = "valid_user_id")
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String validUserId;

    @Column(name = "valid_user_email", nullable = false)
    private String validUserEmail;

    @Column(name = "valid_code", nullable = false)
    private String validCode;

    // , insertable = false, nullable = false
    @Column(name = "valid_active")
    @ColumnDefault("1")
    private Integer validActive;


}
