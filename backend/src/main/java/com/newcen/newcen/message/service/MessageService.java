package com.newcen.newcen.message.service;

import com.newcen.newcen.common.entity.UserEntity;
import com.newcen.newcen.message.dto.request.MessageSendRequestDTO;
import com.newcen.newcen.message.dto.response.*;
import com.newcen.newcen.message.entity.MessageEntity;
import com.newcen.newcen.message.repository.MessageRepository;
import com.newcen.newcen.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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
                .map(msg -> new MessageReceivedResponseDTO(msg))
                .collect(Collectors.toList());

        return MessageReceivedListResponseDTO.builder()
                .receivedMessageList(receivedMessageList)
                .build();
    }

    // 받은 메세지 상세
    public MessageReceivedDetailResponseDTO receivedMessageDetail(final String userId, final long messageId) {
        List<MessageEntity> receivedMessageEntityList = messageRepository.findByReceiverId(userId);

        receivedMessageEntityList.get(0).getSender().getUserEmail();

        MessageReceivedDetailResponseDTO messageDetail = receivedMessageEntityList.stream()
                .filter(msg -> msg.getMessageId() == messageId)
                .map(msg -> new MessageReceivedDetailResponseDTO(msg))
                .findFirst()
                .get();

        return messageDetail;
    }

    // 보낸 메세지 목록 조회
    public MessageSentListResponseDTO sentMessageList(final String userId) {
        List<MessageEntity> sentMessageEntityList = messageRepository.findBySenderId(userId);

        List<MessageSentResponseDTO> sentMessageList = sentMessageEntityList.stream()
                .map(msg -> new MessageSentResponseDTO(msg))
                .collect(Collectors.toList());

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
                .get();

        return messageDetail;
    }

    // 받는 사람 검색 기능
    public List<MessageReceiverResponseDTO> findReceiver(final String userName) {
        List<UserEntity> receiverList = userRepository.findByUserName(userName);
        List<MessageReceiverResponseDTO> foundReceiverList = receiverList.stream()
                .map(MessageReceiverResponseDTO::new)
                .collect(Collectors.toList());

        return foundReceiverList;
    }

    // 단일 메세지 보내기
    public MessageReceivedListResponseDTO sendMessage(final String senderId, final String receiverId, MessageSendRequestDTO message) {




        

        return null;
    }



}
