package com.newcen.newcen.message.entity;

import com.newcen.newcen.common.entity.UserEntity;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter @ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "messageId")
@Builder
@Entity
@Table(name = "message")
public class MessageEntity {

    @Id
    @Column(name = "message_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long messageId;

    @Column(name = "message_title", nullable = false)
    private String messageTitle;

    @Column(name = "message_content", nullable = false)
    private String messageContent;

    @Column(name = "message_sender", nullable = false)
    private String messageSender;

    @Column(name = "message_receiver", nullable = false)
    private String messageReceiver;

    @Column(name = "message_senddate")
    @CreationTimestamp
    private LocalDateTime messageSenddate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="sender_id")
    private UserEntity sender;

//    @Column(name = "sender_id")
//    private String senderId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="receiver_id")
    private UserEntity receiver;

//    @Column(name = "receiver_id")
//    private String receiverId;








}
