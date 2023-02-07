package com.newcen.newcen.message.repository;

import com.newcen.newcen.common.entity.UserEntity;
import com.newcen.newcen.common.entity.UserRole;
import com.newcen.newcen.message.entity.MessageEntity;
import org.apache.catalina.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class MessageRepositoryTest {


    @Autowired
    MessageRepository messageRepository;

    @Test
    @DisplayName("테스트2 회원이 테스트1 회원에게 메세지를 전송해야한다")
    void sendMessageTest() {

        // given
        UserEntity user1 = UserEntity.builder()
                .userId("test")
                .userEmail("test@naver.com")
                .userPassword("1234")
                .userName("테스트")
                .build();

        UserEntity user2 = UserEntity.builder()
                .userId("test2")
                .userEmail("test2@naver.com")
                .userPassword("1234")
                .userName("테스트2")
                .build();

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
    @DisplayName("테스트2 회원이 받은 메세지 리스트의 길이는 1이어야다")
    void findReceivedMessage() {

        // given
        String receiverId = "test2";

        // when
        List<MessageEntity> receivedMessage = messageRepository.findByReceiverId(receiverId);

        // then
        Assertions.assertEquals(1, receivedMessage.size());
        Assertions.assertEquals("테스트2", receivedMessage.get(0).getMessageReceiver());
        Assertions.assertEquals("테스트", receivedMessage.get(0).getMessageSender());
    }

    @Test
    @DisplayName("회원1이 회원2와 회원3에게 메세지를 보내야 한다")
    void SendMessageToUsersTest() {

    }

}