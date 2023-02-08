package com.newcen.newcen.message.service;

import com.newcen.newcen.common.entity.UserEntity;
import com.newcen.newcen.message.dto.request.MessageSendRequestDTO;
import com.newcen.newcen.message.dto.response.*;
import com.newcen.newcen.users.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class MessageServiceTest {

    @Autowired
    MessageService messageService;

    @Autowired
    UserRepository userRepository;

    @BeforeEach
    void beforeTest() {
        UserEntity user1 = UserEntity.builder()
                .userEmail("test1@naver.com")
                .userPassword("1234")
                .userName("회원1")
                .build();

        UserEntity user2 = UserEntity.builder()
                .userEmail("test2@naver.com")
                .userPassword("1234")
                .userName("회원2")
                .build();

        UserEntity user3 = UserEntity.builder()
                .userEmail("test3@naver.com")
                .userPassword("1234")
                .userName("회원3")
                .build();

        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);

    }


    @Test
    @DisplayName("회원1이 회원2에게 메세지를 보내야 한다")
    @Transactional
    void sendOneMessageTest() {

        // given
        UserEntity sender = userRepository.findByUserEmail("test1@naver.com");
        UserEntity receiver = userRepository.findByUserEmail("test2@naver.com");
        String senderId = sender.getUserId();
        String receiverId = receiver.getUserId();

        MessageSendRequestDTO message = MessageSendRequestDTO.builder()
                .messageTitle("단일 메세지 보내기 테스트")
                .messageContent("한번에 성공하겠지?!")
                .messageSender(sender.getUserName())
                .messageReceiver(receiver.getUserName())
                .build();

        // when
        MessageReceivedListResponseDTO messageList = messageService.sendMessage(senderId, receiverId, message);

        // then
        Assertions.assertEquals(1, messageList.getReceivedMessageList().size());
        Assertions.assertEquals("단일 메세지 보내기 테스트", messageList.getReceivedMessageList().get(0).getMessageTitle());

    }

    @Test
    @DisplayName("회원2가 보낸 메세지는 총 3개이다")
    @Transactional
    void sentMessageListTest() {

        // given
        UserEntity sender = userRepository.findByUserEmail("test2@naver.com");
        UserEntity receiver = userRepository.findByUserEmail("test1@naver.com");
        String senderId = sender.getUserId();
        String receiverId = receiver.getUserId();

        MessageSendRequestDTO message = MessageSendRequestDTO.builder()
                .messageTitle("보낸 메세지 조회 테스트")
                .messageContent("한번에 성공하겠지?!?!")
                .messageSender(sender.getUserName())
                .messageReceiver(receiver.getUserName())
                .build();

        for(int i=0; i<3; i++) {
            messageService.sendMessage(senderId, receiverId, message);
        }

        // when
        MessageSentListResponseDTO messageList = messageService.sentMessageList(senderId);

        // then
        Assertions.assertEquals(3, messageList.getSentMessageList().size());
        for(MessageSentResponseDTO msg : messageList.getSentMessageList()) {
            Assertions.assertEquals("회원1", msg.getMessageReceiver());
        }
    }


    @Test
    @DisplayName("회원1이 보낸 두번째 메세지의 제목은 메세지2 이다.")
    @Transactional
    void sentMessageDetailTest() {

        // given
        UserEntity sender = userRepository.findByUserEmail("test1@naver.com");
        UserEntity receiver = userRepository.findByUserEmail("test3@naver.com");
        String senderId = sender.getUserId();
        String receiverId = receiver.getUserId();

        for(int i=1; i<=3; i++) {
            MessageSendRequestDTO message = MessageSendRequestDTO.builder()
                    .messageTitle("메세지"+i)
                    .messageContent(i+"번째 메세지 입니다.")
                    .messageSender(sender.getUserName())
                    .messageReceiver(receiver.getUserName())
                    .build();

            messageService.sendMessage(senderId, receiverId, message);
        }

        // when
        MessageSentListResponseDTO messageList = messageService.sentMessageList(senderId);
        MessageSentResponseDTO secondMessage = messageList.getSentMessageList().get(1);
        long messageId = secondMessage.getMessageId();

        MessageSentDetailResponseDTO secondMessageDetail = messageService.sentMessageDetail(senderId, messageId);

        // then
        Assertions.assertEquals("메세지2", secondMessageDetail.getMessageTitle());

    }

    @Test
    @DisplayName("회원3이 받은 메세지는 총3개이다")
    @Transactional
    void receivedMessageListTest() {

        // given
        UserEntity sender = userRepository.findByUserEmail("test1@naver.com");
        UserEntity receiver = userRepository.findByUserEmail("test3@naver.com");
        String senderId = sender.getUserId();
        String receiverId = receiver.getUserId();

        MessageSendRequestDTO message = MessageSendRequestDTO.builder()
                .messageTitle("받은 메세지 조회 테스트")
                .messageContent("받는 이: 회원3")
                .messageSender(sender.getUserName())
                .messageReceiver(receiver.getUserName())
                .build();

        for(int i=0; i<3; i++) {
            messageService.sendMessage(senderId, receiverId, message);
        }

        // when
        MessageReceivedListResponseDTO messageList = messageService.receivedMessageList(receiverId);

        // then
        Assertions.assertEquals(3, messageList.getReceivedMessageList().size());
        for(MessageReceivedResponseDTO msg : messageList.getReceivedMessageList()) {
            Assertions.assertEquals("회원1", msg.getMessageSender());
        }
    }

    @Test
    @DisplayName("회원3이 받은 세번째 메세지의 제목은 메세지3 이다.")
    @Transactional
    void receivedMessageDetailTest() {

        // given
        UserEntity sender = userRepository.findByUserEmail("test2@naver.com");
        UserEntity receiver = userRepository.findByUserEmail("test3@naver.com");
        String senderId = sender.getUserId();
        String receiverId = receiver.getUserId();

        for(int i=1; i<=3; i++) {
            MessageSendRequestDTO message = MessageSendRequestDTO.builder()
                    .messageTitle("메세지"+i)
                    .messageContent(i+"번째 메세지 입니다.")
                    .messageSender(sender.getUserName())
                    .messageReceiver(receiver.getUserName())
                    .build();

            messageService.sendMessage(senderId, receiverId, message);
        }

        // when
        MessageReceivedListResponseDTO messageList = messageService.receivedMessageList(receiverId);
        MessageReceivedResponseDTO thirdMessage = messageList.getReceivedMessageList().get(2);
        long messageId = thirdMessage.getMessageId();

        MessageReceivedDetailResponseDTO thirdMessageDetail = messageService.receivedMessageDetail(receiverId, messageId);

        // then
        Assertions.assertEquals("메세지3", thirdMessageDetail.getMessageTitle());

    }

    @Test
    @DisplayName("회원3이 받은 메세지중 2번째 메세지를 삭제해야 한다")
    @Transactional
    void deleteMessageOneTest() {

        // given
        UserEntity sender = userRepository.findByUserEmail("test1@naver.com");
        UserEntity receiver = userRepository.findByUserEmail("test3@naver.com");
        String senderId = sender.getUserId();
        String receiverId = receiver.getUserId();

        for(int i=1; i<=3; i++) {
            MessageSendRequestDTO message = MessageSendRequestDTO.builder()
                    .messageTitle("메세지"+i)
                    .messageContent(i+"번째 메세지 입니다.")
                    .messageSender(sender.getUserName())
                    .messageReceiver(receiver.getUserName())
                    .build();

            messageService.sendMessage(senderId, receiverId, message);
        }


        // when
        MessageReceivedListResponseDTO messageList = messageService.receivedMessageList(receiverId);
        MessageReceivedResponseDTO targetMessage = messageList.getReceivedMessageList().get(1);
        long messageId = targetMessage.getMessageId();

        MessageReceivedListResponseDTO afterDeleteMessageList = messageService.deleteMessage(messageId, receiverId);

        // then
        Assertions.assertEquals(2, afterDeleteMessageList.getReceivedMessageList().size());
        Assertions.assertEquals("메세지1", afterDeleteMessageList.getReceivedMessageList().get(0).getMessageTitle());
        Assertions.assertEquals("메세지3", afterDeleteMessageList.getReceivedMessageList().get(1).getMessageTitle());


    }



}