package com.newcen.newcen.message.service;

import com.newcen.newcen.common.dto.request.SearchCondition;
import com.newcen.newcen.common.dto.request.SearchReceivedMessageCondition;
import com.newcen.newcen.common.dto.request.SearchSentMessageCondition;
import com.newcen.newcen.common.entity.UserEntity;
import com.newcen.newcen.message.dto.request.MessageSendRequestDTO;
import com.newcen.newcen.message.dto.response.*;
import com.newcen.newcen.message.entity.MessageEntity;
import com.newcen.newcen.message.exception.MessageCustomException;
import com.newcen.newcen.message.exception.MessageExceptionEnum;
import com.newcen.newcen.message.repository.MessageRepository;
import com.newcen.newcen.message.repository.MessageRepositorySupport;
import com.newcen.newcen.question.response.QuestionResponseDTO;
import com.newcen.newcen.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;

    private final MessageRepositorySupport messageRepositorySupport;

    private final UserRepository userRepository;


    // 받은 메세지 목록 조회
    public MessageReceivedListResponseDTO receivedMessageList(final String userId) {

        userRepository.findByUserId(userId).orElseThrow(() -> {
            throw new MessageCustomException(MessageExceptionEnum.UNAUTHORIZED_ACCESS);
        });

        List<MessageEntity> receivedMessageEntityList = messageRepository.findByReceiverId(userId);

        List<MessageReceivedResponseDTO> receivedMessageList = receivedMessageEntityList.stream()
                .map(MessageReceivedResponseDTO::new)
                .collect(Collectors.toList());

        log.info("받은 메세지 목록 조회 - 받은 메세지 수: {}", receivedMessageList.size() );

        return MessageReceivedListResponseDTO.builder()
                .receivedMessageList(receivedMessageList)
                .build();
    }
    // 받은 메세지 목록 조회 페이지 제네이션
    public PageImpl<MessageReceivedResponseDTO> getReceivedMessagePageList(Pageable pageable, String userId){
        PageImpl<MessageReceivedResponseDTO> result = messageRepositorySupport.getReceivedMessage(pageable,userId);
        return result;
    }
    // 보낸 메세지 목록 조회 페이지 제네이션
    public PageImpl<MessageSentResponseDTO> getSentMessagePageList(Pageable pageable, String userId){
        PageImpl<MessageSentResponseDTO> result = messageRepositorySupport.getSentMessage(pageable,userId);
        return result;
    }
    //받은 메세지 목록 검색 조회 페이지 제네이션
    public PageImpl<MessageReceivedResponseDTO> getReceivedMessagePageListWithSearch(SearchReceivedMessageCondition searchReceivedMessageCondition, Pageable pageable, String userId){
        if (searchReceivedMessageCondition.getMessageContent().isEmpty() && searchReceivedMessageCondition.getMessageSender().isEmpty()  && searchReceivedMessageCondition.getMessageTitle().isEmpty()){
            PageImpl<MessageReceivedResponseDTO> result = messageRepositorySupport.getReceivedMessage(pageable,userId);
            return result;
        }
        PageImpl<MessageReceivedResponseDTO> result = messageRepositorySupport.getReceivedMessageWithSearch(searchReceivedMessageCondition,pageable,userId);
        return result;
    }
    //보낸 메세지 목록 검색 조회 페이지 제네이션
    public PageImpl<MessageSentResponseDTO> getSentMessagePageListWithSearch(SearchSentMessageCondition searchSentMessageCondition, Pageable pageable, String userId){
        if (searchSentMessageCondition.getMessageContent().isEmpty()  && searchSentMessageCondition.getMessageReceiver().isEmpty()  && searchSentMessageCondition.getMessageTitle().isEmpty() ){
            PageImpl<MessageSentResponseDTO> result = messageRepositorySupport.getSentMessage(pageable,userId);
            return result;
        }
        PageImpl<MessageSentResponseDTO> result = messageRepositorySupport.getSentMessageWithSearch(searchSentMessageCondition,pageable,userId);
        return result;
    }

    // 받은 메세지 상세
    public MessageReceivedDetailResponseDTO receivedMessageDetail(final String userId, final long messageId) {

        userRepository.findByUserId(userId).orElseThrow(() -> {
            throw new MessageCustomException(MessageExceptionEnum.UNAUTHORIZED_ACCESS);
        });

        List<MessageEntity> receivedMessageEntityList = messageRepository.findByReceiverId(userId);

        MessageReceivedDetailResponseDTO messageDetail = receivedMessageEntityList.stream()
                .filter(msg -> msg.getMessageId() == messageId)
                .map(MessageReceivedDetailResponseDTO::new)
                .findFirst()
                .orElseThrow(() -> new MessageCustomException(MessageExceptionEnum.MESSAGE_NOT_FOUND));

        log.info("받은 메세지 상세 - 메세지 제목: {}", messageDetail.getMessageTitle());

        return messageDetail;
    }

    // 보낸 메세지 목록 조회
    public MessageSentListResponseDTO sentMessageList(final String userId) {

        userRepository.findByUserId(userId).orElseThrow(() -> {
            throw new MessageCustomException(MessageExceptionEnum.UNAUTHORIZED_ACCESS);
        });

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

        userRepository.findByUserId(userId).orElseThrow(() -> {
            throw new MessageCustomException(MessageExceptionEnum.UNAUTHORIZED_ACCESS);
        });

        List<MessageEntity> sentMessageEntityList = messageRepository.findBySenderId(userId);

        MessageSentDetailResponseDTO messageDetail = sentMessageEntityList.stream()
                .filter(msg -> msg.getMessageId() == messageId)
                .map(MessageSentDetailResponseDTO::new)
                .findFirst()
                .orElseThrow(() -> new MessageCustomException(MessageExceptionEnum.MESSAGE_NOT_FOUND));

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
    @Transactional
    public boolean sendMessage(final String senderId, final List<String> receiverId, final MessageSendRequestDTO message) {

            UserEntity sender = userRepository.findByUserId(senderId).orElseThrow(() -> {
                throw new MessageCustomException(MessageExceptionEnum.UNAUTHORIZED_ACCESS);
            });

            if(receiverId.isEmpty()) {
                throw new InvalidParameterException();
            }

            for(String id : receiverId) {
                UserEntity receiver = userRepository.findByUserId(id).orElseThrow(() -> {
                    throw new MessageCustomException(MessageExceptionEnum.USER_NOT_EXIST);
                });
                MessageEntity sendMessage = message.toEntity(sender, receiver);
                MessageEntity savedMessage = messageRepository.save(sendMessage);

                log.info("메세지 전송 완료 보낸 이: {}, 받는 이: {}", savedMessage.getMessageSender(), savedMessage.getMessageReceiver());




            }
        return true;
    }



    // 메세지 삭제
    @Transactional
    public boolean deleteMessage(final List<Long> messageId, final String userId) {

        for(long id : messageId) {
            messageRepository.findById(id).orElseThrow(() -> {
                throw new MessageCustomException(MessageExceptionEnum.MESSAGE_NOT_FOUND);
            });
            messageRepository.deleteByMessageId(id);
        }

        return true;
    }



}
