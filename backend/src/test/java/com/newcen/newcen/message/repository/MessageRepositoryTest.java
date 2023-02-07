package com.newcen.newcen.message.repository;

import com.newcen.newcen.common.entity.UserEntity;
import com.newcen.newcen.common.entity.UserRole;
import com.newcen.newcen.message.entity.MessageEntity;
import com.newcen.newcen.users.repository.UserRepository;
import org.apache.catalina.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class MessageRepositoryTest {


    @Autowired
    MessageRepository messageRepository;

    @Autowired
    UserRepository userRepository;


    @Test
    @DisplayName("테스트2 회원이 테스트1 회원에게 메세지를 전송해야한다")
    void sendMessageTest() {

        // given
        UserEntity user1 = UserEntity.builder()
                .userEmail("test@naver.com")
                .userPassword("1234")
                .userName("테스트")
                .userRole(UserRole.MEMBER)
                .build();

        UserEntity user2 = UserEntity.builder()
                .userEmail("test2@naver.com")
                .userPassword("1234")
                .userName("테스트2")
                .userRole(UserRole.MEMBER)
                .build();

        userRepository.save(user1);
        userRepository.save(user2);

        MessageEntity message = MessageEntity.builder()
                .messageTitle("보내기 테스트 제목입니다.")
                .messageContent("보내기 테스트 내용입니다.")
                .messageSender(user1.getUserName())
                .messageReceiver(user2.getUserName())
                .sender(user1)
                .receiver(user2)
                .build();

        // when
        MessageEntity savedMessage = messageRepository.save(message);

        // then
        Assertions.assertEquals("테스트", savedMessage.getMessageSender());
        Assertions.assertEquals("테스트2", savedMessage.getMessageReceiver());
        Assertions.assertEquals("보내기 테스트 제목입니다.", savedMessage.getMessageTitle());
        Assertions.assertEquals("보내기 테스트 내용입니다.", savedMessage.getMessageContent());

    }

    @Test
    @DisplayName("테스트2 회원이 받은 메세지 리스트의 길이는 2이다")
    void findReceivedMessage() {

        // given
        UserEntity receiver = userRepository.findByUserEmail("test2@naver.com");

        // when
        List<MessageEntity> receivedMessage = messageRepository.findByReceiverId(receiver.getUserId());

        // then
        Assertions.assertEquals(2, receivedMessage.size());
        Assertions.assertEquals("테스트2", receivedMessage.get(0).getMessageReceiver());
        Assertions.assertEquals("테스트", receivedMessage.get(0).getMessageSender());
        Assertions.assertEquals("테스트2", receivedMessage.get(1).getMessageReceiver());
        Assertions.assertEquals("테스트", receivedMessage.get(1).getMessageSender());
    }

    @Test
    @DisplayName("테스트가 테스트2와 테스트3에게 메세지를 보내야 한다")
    void SendMessageToUsersTest() {

        // given
        UserEntity user3 = UserEntity.builder()
                .userEmail("test3@naver.com")
                .userPassword("1234")
                .userName("테스트3")
                .userRole(UserRole.MEMBER)
                .build();

        userRepository.save(user3);

        UserEntity sendUser = userRepository.findByUserEmail("test@naver.com");
        UserEntity targetUser1 = userRepository.findByUserEmail("test2@naver.com");
        UserEntity targetUser2 = userRepository.findByUserEmail("test3@naver.com");

        List<UserEntity> targetList = new ArrayList<>();
        targetList.add(targetUser1);
        targetList.add(targetUser2);

        // when
        List<MessageEntity> messageList = new ArrayList<>();

        for(UserEntity target : targetList) {
            MessageEntity message = MessageEntity.builder()
                    .messageTitle("단체 메세지 테스트 제목입니다.")
                    .messageContent("단체 메세지 테스트 내용입니다.")
                    .messageSender(sendUser.getUserName())
                    .messageReceiver(target.getUserName())
                    .sender(sendUser)
                    .receiver(target)
                    .build();

            messageList.add(messageRepository.save(message));
        }

        // then
        Assertions.assertEquals(2, messageList.size());

        for(MessageEntity message : messageList) {
            Assertions.assertEquals("테스트", message.getMessageSender());
        }
    }


    @Test
    @DisplayName("회원이 보낸 메세지는 총 3개여야 한다")
    void findSendMessage() {

        // given
        UserEntity targetUser = userRepository.findByUserEmail("test@naver.com");

        // when
        List<MessageEntity> sendMessageList = messageRepository.findBySenderId(targetUser.getUserId());

        // then
        Assertions.assertEquals(3, sendMessageList.size());
        for(MessageEntity message : sendMessageList) {
            Assertions.assertEquals("테스트", message.getMessageSender());
        }

    }


    @Test
    @DisplayName("테스트2 회원이 받은 메세지중 첫번째 메세지만 삭제한다")
    @Transactional
    @Commit
    void deleteFirstReceivedMessage() {

        // given
        String targetUserId = userRepository.findByUserEmail("test2@naver.com").getUserId();
        List<MessageEntity> receivedMessageList = messageRepository.findByReceiverId(targetUserId);

        // when
        long targetMessageId = receivedMessageList.get(0).getMessageId();
        messageRepository.deleteByMessageId(targetMessageId);

        List<MessageEntity> newReceivedMessageList = messageRepository.findByReceiverId(targetUserId);

        // then
        Assertions.assertEquals(1, newReceivedMessageList.size());
        Assertions.assertEquals("테스트2", newReceivedMessageList.get(0).getMessageReceiver());


    }






}