package com.newcen.newcen.message.service;

import com.newcen.newcen.common.entity.UserEntity;
import com.newcen.newcen.message.dto.request.MessageSendRequestDTO;
import com.newcen.newcen.message.dto.response.MessageReceivedDetailResponseDTO;
import com.newcen.newcen.message.dto.response.MessageReceivedListResponseDTO;
import com.newcen.newcen.message.dto.response.MessageReceivedResponseDTO;
import com.newcen.newcen.message.dto.response.MessageSentListResponseDTO;
import com.newcen.newcen.users.repository.UserRepository;
import net.bytebuddy.asm.Advice;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MessageServiceTest {

    @Autowired
    MessageService messageService;

    @Autowired
    UserRepository userRepository;

    @Test
    @Order(1)
    @DisplayName("테스트 시작 전 회원1, 회원2, 회원3 추가")
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
    @Order(100)
    void sendOneMessageTest() {

        // given
        UserEntity sender = userRepository.findByUserEmail("test1@naver.com");
        UserEntity receiver = userRepository.findByUserEmail("test2@naver.com");
        String senderId = sender.getUserId();
        String receiverId = receiver.getUserId();

        List<String> receiverList = new ArrayList<>();
        receiverList.add(receiverId);

        MessageSendRequestDTO message = MessageSendRequestDTO.builder()
                .messageTitle("메세지 보내기 테스트")
                .messageContent("한번에 성공하겠지?!")
                .messageSender(sender.getUserName())
                .build();

        // when
        messageService.sendMessage(senderId, receiverList, message);
        MessageSentListResponseDTO sentMessageList = messageService.sentMessageList(senderId);

        // then
        Assertions.assertEquals(1, sentMessageList.getSentMessageList().size());
        Assertions.assertEquals("메세지 보내기 테스트", sentMessageList.getSentMessageList().get(0).getMessageTitle());

    }

    @Test
    @DisplayName("회원2가 보낸 메세지는 총 3개이다 (회원1 2개 회원3 1개)")
    @Order(200)
//    @Transactional
    void sentMessageListTest() {

        // given
        UserEntity sender = userRepository.findByUserEmail("test2@naver.com");
        UserEntity receiver1 = userRepository.findByUserEmail("test1@naver.com");
        UserEntity receiver3 = userRepository.findByUserEmail("test3@naver.com");

        String senderId = sender.getUserId();
        String receiverId1 = receiver1.getUserId();
        String receiverId3 = receiver3.getUserId();

        List<String> receiverList = new ArrayList<>();
        receiverList.add(receiverId1);
        receiverList.add(receiverId1);
        receiverList.add(receiverId3);


        MessageSendRequestDTO message = MessageSendRequestDTO.builder()
                .messageTitle("보낸 메세지 조회 테스트")
                .messageContent("한번에 성공하겠지?!?!")
                .messageSender(sender.getUserName())
                .build();


        // when
        messageService.sendMessage(senderId, receiverList, message);
        MessageSentListResponseDTO messageList = messageService.sentMessageList(senderId);

        // then
        Assertions.assertEquals(3, messageList.getSentMessageList().size());

    }

    @Test
    @DisplayName("회원1이 받은 메세지는 총2개이다")
    @Order(300)
    void receivedMessageListTest() {

        // given
        UserEntity receiver = userRepository.findByUserEmail("test1@naver.com");
        String receiverId = receiver.getUserId();

        // when
        MessageReceivedListResponseDTO messageList = messageService.receivedMessageList(receiverId);

        // then
        Assertions.assertEquals(2, messageList.getReceivedMessageList().size());

    }

    @Test
    @DisplayName("회원3이 받은 메세지는 회원2가 보낸 것이고 제목은 보낸 메세지 조회 테스트 이다.")
    @Order(400)
    @Transactional
    void receivedMessageDetailTest() {

        // given
        UserEntity receiver = userRepository.findByUserEmail("test3@naver.com");
        String receiverId = receiver.getUserId();

        // when
        MessageReceivedListResponseDTO messageList = messageService.receivedMessageList(receiverId);
        MessageReceivedResponseDTO message = messageList.getReceivedMessageList().get(0);
        long messageId = message.getMessageId();

        MessageReceivedDetailResponseDTO messageDetail = messageService.receivedMessageDetail(receiverId, messageId);

        // then
        Assertions.assertEquals("회원2", message.getMessageSender());
        Assertions.assertEquals("보낸 메세지 조회 테스트", messageDetail.getMessageTitle());

    }

    @Test
    @DisplayName("회원1이 받은 메세지중 2번째 메세지를 삭제해야 한다")
    @Order(500)
    @Transactional
    void deleteMessageOneTest() {

        // given
        UserEntity receiver = userRepository.findByUserEmail("test1@naver.com");
        String receiverId = receiver.getUserId();

        // when
        MessageReceivedListResponseDTO messageList = messageService.receivedMessageList(receiverId);
        MessageReceivedResponseDTO targetMessage = messageList.getReceivedMessageList().get(1);
        long messageId = targetMessage.getMessageId();

        List<Long> deleteMessageList = new ArrayList<>();
        deleteMessageList.add(messageId);

        MessageReceivedListResponseDTO afterDeleteMessageList = messageService.deleteMessage(deleteMessageList, receiverId);

        // then
        Assertions.assertEquals(1, afterDeleteMessageList.getReceivedMessageList().size());
        Assertions.assertEquals("회원2", afterDeleteMessageList.getReceivedMessageList().get(0).getMessageSender());

    }



}