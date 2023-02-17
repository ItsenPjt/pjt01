package com.newcen.newcen.message.repository;

import com.newcen.newcen.comment.dto.request.FixedPageRequest;
import com.newcen.newcen.common.dto.request.SearchReceivedMessageCondition;
import com.newcen.newcen.common.dto.request.SearchSentMessageCondition;
import com.newcen.newcen.message.dto.response.MessageReceivedDetailResponseDTO;
import com.newcen.newcen.message.dto.response.MessageReceivedListResponseDTO;
import com.newcen.newcen.message.dto.response.MessageReceivedResponseDTO;
import com.newcen.newcen.message.dto.response.MessageSentResponseDTO;
import com.newcen.newcen.message.entity.MessageEntity;
import com.newcen.newcen.message.entity.QMessageEntity;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class MessageRepositorySupport extends QuerydslRepositorySupport {

    public MessageRepositorySupport(JPAQueryFactory jpaQueryFactory) {
        super(MessageEntity.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    private final JPAQueryFactory jpaQueryFactory;

    QMessageEntity qMessageEntity = QMessageEntity.messageEntity;


    //받은 메세지 목록 조회
    public PageImpl<MessageReceivedResponseDTO> getReceivedMessage(Pageable pageable, String userId){
        JPQLQuery<MessageEntity> query = jpaQueryFactory.select(qMessageEntity)
                .from(qMessageEntity)
                .where(qMessageEntity.receiver.userId.eq(userId))
                .orderBy(qMessageEntity.messageSenddate.desc());

        long totalCount = query.fetchCount();
        List<MessageEntity> results = getQuerydsl().applyPagination(pageable, query).fetch();
        List<MessageReceivedResponseDTO> dtoList = results.stream()
                .map(MessageReceivedResponseDTO::new)
                .collect(Collectors.toList());

        Pageable pageRequest = new FixedPageRequest(pageable,totalCount);

        return new PageImpl<>(dtoList, pageRequest, totalCount);
    }

    //보낸 메세지 목록 조회
    public PageImpl<MessageSentResponseDTO> getSentMessage(Pageable pageable, String userId){
        JPQLQuery<MessageEntity> query = jpaQueryFactory.select(qMessageEntity)
                .from(qMessageEntity)
                .where(qMessageEntity.sender.userId.eq(userId))
                .orderBy(qMessageEntity.messageSenddate.desc());

        long totalCount = query.fetchCount();
        List<MessageEntity> results = getQuerydsl().applyPagination(pageable, query).fetch();
        List<MessageSentResponseDTO> dtoList = results.stream()
                .map(MessageSentResponseDTO::new)
                .collect(Collectors.toList());

        Pageable pageRequest = new FixedPageRequest(pageable,totalCount);

        return new PageImpl<>(dtoList, pageRequest, totalCount);
    }

    //받은 메세지 검색 페이지
    public PageImpl<MessageReceivedResponseDTO> getReceivedMessageWithSearch(SearchReceivedMessageCondition searchReceivedMessageCondition, Pageable pageable, String userId){
        JPQLQuery<MessageEntity> query = jpaQueryFactory.select(qMessageEntity)
                .from(qMessageEntity)
                .where(qMessageEntity.receiver.userId.eq(userId),
//                        messageTitleEq(searchReceivedMessageCondition.getMessageTitle()),
//                        messageContentEq(searchReceivedMessageCondition.getMessageContent()),
                        messageSenderEq(searchReceivedMessageCondition.getMessageSender()),
                        ContentMessageTitleEq(searchReceivedMessageCondition.getMessageContent(),searchReceivedMessageCondition.getMessageTitle())
                        )
                .orderBy(qMessageEntity.messageSenddate.desc());

        long totalCount = query.fetchCount();
        List<MessageEntity> results = getQuerydsl().applyPagination(pageable, query).fetch();
        List<MessageReceivedResponseDTO> dtoList = results.stream()
                .map(MessageReceivedResponseDTO::new)
                .collect(Collectors.toList());

        Pageable pageRequest = new FixedPageRequest(pageable,totalCount);

        return new PageImpl<>(dtoList, pageRequest, totalCount);
    }
    private BooleanExpression ContentMessageTitleEq(String messageContent,String messageTitle){

        if(!messageTitle.isEmpty() && !messageContent.isEmpty()){
            return qMessageEntity.messageContent.contains(messageContent).or(qMessageEntity.messageTitle.contains(messageTitle));
        }
        if(!messageTitle.isEmpty() && messageContent.isEmpty()){
            return qMessageEntity.messageTitle.contains(messageTitle);
        }
        if(messageTitle.isEmpty() && !messageContent.isEmpty()){
            return qMessageEntity.messageContent.contains(messageContent);
        }
        return null;
    }


    private BooleanExpression messageTitleEq(String messageTitle){
        if(messageTitle.isEmpty()){
            return null;
        }
        return qMessageEntity.messageTitle.contains(messageTitle);
    }

    private BooleanExpression messageContentEq(String messageContent){
        if(messageContent.isEmpty()){
            return null;
        }
        return qMessageEntity.messageContent.contains(messageContent);
    }

    private BooleanExpression messageSenderEq(String messageSender){
        if(messageSender == null || messageSender.isEmpty()){
            return null;
        }
        return qMessageEntity.messageSender.contains(messageSender);
    }
    private BooleanExpression messageReceiverEq(String messageReceiver){
        if(messageReceiver.isEmpty()){
            return null;
        }
        return qMessageEntity.messageReceiver.contains(messageReceiver);
    }

    //보낸 메세지 검색 페이지
    public PageImpl<MessageSentResponseDTO> getSentMessageWithSearch(SearchSentMessageCondition searchSentMessageCondition, Pageable pageable, String userId){
        JPQLQuery<MessageEntity> query = jpaQueryFactory.select(qMessageEntity)
                .from(qMessageEntity)
                .where(qMessageEntity.sender.userId.eq(userId),
                        messageTitleEq(searchSentMessageCondition.getMessageTitle()),
                        ContentMessageTitleEq(searchSentMessageCondition.getMessageContent(),searchSentMessageCondition.getMessageTitle())
//                        messageContentEq(searchSentMessageCondition.getMessageContent()),
//                        messageReceiverEq(searchSentMessageCondition.getMessageReceiver())
                )
                .orderBy(qMessageEntity.messageSenddate.desc());

        long totalCount = query.fetchCount();
        List<MessageEntity> results = getQuerydsl().applyPagination(pageable, query).fetch();
        List<MessageSentResponseDTO> dtoList = results.stream()
                .map(MessageSentResponseDTO::new)
                .collect(Collectors.toList());

        Pageable pageRequest = new FixedPageRequest(pageable,totalCount);

        return new PageImpl<>(dtoList, pageRequest, totalCount);
    }
}
