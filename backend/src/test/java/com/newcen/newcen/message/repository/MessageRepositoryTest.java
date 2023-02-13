package com.newcen.newcen.message.repository;

import com.newcen.newcen.common.entity.UserEntity;
import com.newcen.newcen.common.entity.UserRole;
import com.newcen.newcen.message.entity.MessageEntity;
import com.newcen.newcen.users.repository.UserRepository;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Transactional
class MessageRepositoryTest {


    @Autowired
    MessageRepository messageRepository;

    @Autowired
    UserRepository userRepository;


    @BeforeEach
    void beforeTest() {
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

        UserEntity user3 = UserEntity.builder()
                .userEmail("test3@naver.com")
                .userPassword("1234")
                .userName("테스트3")
                .userRole(UserRole.MEMBER)
                .build();

        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);

    }


    @Test
    @DisplayName("테스트2 회원이 테스트1 회원에게 메세지를 전송해야한다")
    void sendMessageTest() {

        // given

        UserEntity user1 = userRepository.findByUserEmail("test@naver.com");
        UserEntity user2 = userRepository.findByUserEmail("test2@naver.com");

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
    @DisplayName("테스트2 회원이 받은 메세지 리스트의 길이는 1이다")
    void findReceivedMessage() {

        // given
        UserEntity user1 = userRepository.findByUserEmail("test@naver.com");
        UserEntity receiver = userRepository.findByUserEmail("test2@naver.com");

        MessageEntity message = MessageEntity.builder()
                .messageTitle("보내기 테스트 제목입니다.")
                .messageContent("보내기 테스트 내용입니다.")
                .messageSender(user1.getUserName())
                .messageReceiver(receiver.getUserName())
                .sender(user1)
                .receiver(receiver)
                .build();

        messageRepository.save(message);

        // when
        List<MessageEntity> receivedMessage = messageRepository.findByReceiverId(receiver.getUserId());

        // then
        Assertions.assertEquals(1, receivedMessage.size());
        Assertions.assertEquals("테스트2", receivedMessage.get(0).getMessageReceiver());
        Assertions.assertEquals("테스트", receivedMessage.get(0).getMessageSender());
    }

    @Test
    @DisplayName("테스트가 테스트2와 테스트3에게 메세지를 보내야 한다")
    void SendMessageToUsersTest() {

        // given


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

        UserEntity sendUser = userRepository.findByUserEmail("test@naver.com");
        UserEntity targetUser = userRepository.findByUserEmail("test2@naver.com");

        for(int i=0; i<3; i++) {
            MessageEntity message = MessageEntity.builder()
                    .messageTitle("단체 메세지 테스트 제목입니다.")
                    .messageContent("단체 메세지 테스트 내용입니다.")
                    .messageSender(sendUser.getUserName())
                    .messageReceiver(targetUser.getUserName())
                    .sender(sendUser)
                    .receiver(targetUser)
                    .build();

            messageRepository.save(message);
        }

        // when
        List<MessageEntity> sendMessageList = messageRepository.findBySenderId(sendUser.getUserId());

        // then
        Assertions.assertEquals(3, sendMessageList.size());
        for(MessageEntity message : sendMessageList) {
            Assertions.assertEquals("테스트", message.getMessageSender());
        }

    }


    @Test
    @DisplayName("테스트2 회원이 받은 메세지중 첫번째 메세지만 삭제한다")
    void deleteFirstReceivedMessage() {

        // given

        UserEntity sendUser = userRepository.findByUserEmail("test@naver.com");
        UserEntity targetUser = userRepository.findByUserEmail("test2@naver.com");

        for(int i=0; i<3; i++) {
            MessageEntity message = MessageEntity.builder()
                    .messageTitle("삭제 메세지 테스트 제목입니다.")
                    .messageContent("삭제 메세지 테스트 내용입니다.")
                    .messageSender(sendUser.getUserName())
                    .messageReceiver(targetUser.getUserName())
                    .sender(sendUser)
                    .receiver(targetUser)
                    .build();

            messageRepository.save(message);
        }


        List<MessageEntity> receivedMessageList = messageRepository.findByReceiverId(targetUser.getUserId());

        // when
        long targetMessageId = receivedMessageList.get(0).getMessageId();
        messageRepository.deleteByMessageId(targetMessageId);

        List<MessageEntity> newReceivedMessageList = messageRepository.findByReceiverId(targetUser.getUserId());

        // then
        Assertions.assertEquals(2, newReceivedMessageList.size());
        Assertions.assertEquals("테스트2", newReceivedMessageList.get(0).getMessageReceiver());


    }






}