package com.newcen.newcen.message.service;

import com.newcen.newcen.common.entity.UserEntity;
import com.newcen.newcen.message.dto.request.MessageSendRequestDTO;
import com.newcen.newcen.message.dto.response.*;
import com.newcen.newcen.message.entity.MessageEntity;
import com.newcen.newcen.message.repository.MessageRepository;
import com.newcen.newcen.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;

    private final UserRepository userRepository;


    // 받은 메세지 목록 조회
    public MessageReceivedListResponseDTO receivedMessageList(final String userId) {

        List<MessageEntity> receivedMessageEntityList = messageRepository.findByReceiverId(userId);

        List<MessageReceivedResponseDTO> receivedMessageList = receivedMessageEntityList.stream()
                .map(MessageReceivedResponseDTO::new)
                .collect(Collectors.toList());
        
        log.info("받은 메세지 목록 조회 - 받은 메세지 수: {}", receivedMessageList.size() );

        return MessageReceivedListResponseDTO.builder()
                .receivedMessageList(receivedMessageList)
                .build();
    }

    // 받은 메세지 상세
    public MessageReceivedDetailResponseDTO receivedMessageDetail(final String userId, final long messageId) {
        List<MessageEntity> receivedMessageEntityList = messageRepository.findByReceiverId(userId);

        MessageReceivedDetailResponseDTO messageDetail = receivedMessageEntityList.stream()
                .filter(msg -> msg.getMessageId() == messageId)
                .map(MessageReceivedDetailResponseDTO::new)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Message Does Not Exist"));

        log.info("받은 메세지 상세 - 메세지 제목: {}", messageDetail.getMessageTitle());

        return messageDetail;
    }

    // 보낸 메세지 목록 조회
    public MessageSentListResponseDTO sentMessageList(final String userId) {
        List<MessageEntity> sentMessageEntityList = messageRepository.findBySenderId(userId);

        List<MessageSentResponseDTO> sentMessageList = sentMessageEntityList.stream()
                .map(MessageSentResponseDTO::new)
                .collect(Collectors.toList());

        log.info("보낸 메세지 목록 조회 - 보낸 메세지 수: {}", sentMessageList.size() );

        return MessageSentListResponseDTO.builder()
                .sentMessageList(sentMessageList)
                .build();
    }

    // 보낸 메세지 상세
    public MessageSentDetailResponseDTO sentMessageDetail(final String userId, final long messageId) {
        List<MessageEntity> sentMessageEntityList = messageRepository.findBySenderId(userId);

        MessageSentDetailResponseDTO messageDetail = sentMessageEntityList.stream()
                .filter(msg -> msg.getMessageId() == messageId)
                .map(MessageSentDetailResponseDTO::new)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Message Does Not Exist"));

        log.info("보낸 메세지 상세 - 메세지 제목: {}", messageDetail.getMessageTitle());

        return messageDetail;
    }

    // 받는 사람 검색 기능
    public List<MessageReceiverResponseDTO> findReceiver(final String userName) {
        List<UserEntity> receiverList = userRepository.findByUserNameContains(userName);
        List<MessageReceiverResponseDTO> foundReceiverList = receiverList.stream()
                .map(MessageReceiverResponseDTO::new)
                .collect(Collectors.toList());

        log.info("받는 이 검색 요청!");

        return foundReceiverList;
    }

    // 메세지 보내기
    public MessageReceivedListResponseDTO sendMessage(final String senderId, final List<String> receiverId, final MessageSendRequestDTO message) {

        UserEntity sender = userRepository.findByUserId(senderId);
        for(String id : receiverId) {
            UserEntity receiver = userRepository.findByUserId(id);
            MessageEntity sendMessage = message.toEntity(sender, receiver);
            MessageEntity savedMessage = messageRepository.save(sendMessage);

            log.info("메세지 전송 완료 보낸 이: {}, 받는 이: {}", savedMessage.getMessageSender(), savedMessage.getMessageReceiver());
        }

        return receivedMessageList(senderId);
    }

    // 메세지 삭제
    public MessageReceivedListResponseDTO deleteMessage(final List<Long> messageId, final String userId) {
        for(long id : messageId) {
            messageRepository.deleteByMessageId(id);
        }

        return receivedMessageList(userId);
    }



}
