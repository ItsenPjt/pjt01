package com.newcen.newcen.mainPage.entity;

import com.newcen.newcen.common.entity.BoardEntity;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter @ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
@Entity
public class MessageEntity {

    @Id
    @Column(name = "message_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long messageId;

    @Column(name = "message_title")
    private String messageTitle;

    @Column(name = "message_content")
    private String messageContent;

    @Column(name = "message_sender")
    private String messageSender;

    @Column(name = "message_receiver")
    private String messageReceiver;

    @Column(name = "message_senddate")
    @CreationTimestamp
    private LocalDateTime messageSenddate;

    @OneToMany(orphanRemoval = true, mappedBy = "message")
    private final List<UserMessageEntity> userMessageEntityList = new ArrayList<>();






}
