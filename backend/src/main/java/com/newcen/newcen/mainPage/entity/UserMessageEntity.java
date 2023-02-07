package com.newcen.newcen.mainPage.entity;

import com.newcen.newcen.common.entity.UserEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

public class UserMessageEntity {

    @Id
    @Column(name = "user_message_id")
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String userMessageId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", insertable = false, updatable = false)
    private UserEntity user;

    @Column(name = "user_id")
    private String usedId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "message_id", insertable = false, updatable = false)
    private String messageId;



}
