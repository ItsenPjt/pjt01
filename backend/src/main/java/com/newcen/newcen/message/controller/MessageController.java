package com.newcen.newcen.message.controller;

import com.newcen.newcen.common.dto.request.SearchReceivedMessageCondition;
import com.newcen.newcen.common.dto.request.SearchSentMessageCondition;
import com.newcen.newcen.message.dto.request.MessageSendRequestDTO;
import com.newcen.newcen.message.dto.response.*;
import com.newcen.newcen.message.exception.MessageCustomException;
import com.newcen.newcen.message.exception.MessageExceptionEntity;
import com.newcen.newcen.message.exception.MessageExceptionEnum;
import com.newcen.newcen.message.service.MessageService;
import com.newcen.newcen.question.response.QuestionResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.InvalidParameterException;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    // 메세지 목록 조회
    @GetMapping("/api/messages")
    public ResponseEntity<?> messageList(@AuthenticationPrincipal String userId,
                                         @RequestParam("mode") String mode, Pageable pageable) {

        log.info("/api/{}/message message list GET request!", userId);

        log.info("pageable : {}", pageable);

        if(mode.equals("received")) {
//            MessageReceivedListResponseDTO messageList = messageService.receivedMessageList(userId);
            PageImpl<MessageReceivedResponseDTO> responseDTO = messageService.getReceivedMessagePageList(pageable,userId);

            log.info("Received Message List Return Success😄");
            return ResponseEntity
                    .ok()
                    .body(responseDTO);
        }else if(mode.equals("sent")) {
//            MessageSentListResponseDTO messageList = messageService.sentMessageList(userId);
            PageImpl<MessageSentResponseDTO> responseDTO = messageService.getSentMessagePageList(pageable,userId);

            log.info("Received Message List Return Success😄");
            return ResponseEntity
                    .ok()
                    .body(responseDTO);
        }else {
            throw new InvalidParameterException();
        }
    }

    // 메세지 상세 조회
    @GetMapping("/api/messages/{messageId}")
    public ResponseEntity<?> receivedMessageDetail(@AuthenticationPrincipal String userId,
                                                   @PathVariable("messageId") Long messageId,
                                                   @RequestParam("mode") String mode) {

        log.info("/api/{}/message/{} message detail GET request!", userId, messageId);

        if(mode.equals("received")) {
            MessageReceivedDetailResponseDTO message = messageService.receivedMessageDetail(userId, messageId);
            return ResponseEntity
                    .ok()
                    .body(message);
        }else if(mode.equals("sent")) {
            MessageSentDetailResponseDTO message = messageService.sentMessageDetail(userId, messageId);
            return ResponseEntity
                    .ok()
                    .body(message);
        }else {
            throw new InvalidParameterException();
        }
    }
    //받은 메세지 검색
    @PostMapping("/api/messages/search/received")
    public ResponseEntity<?> getReceivedPageList(
            @AuthenticationPrincipal String userId,
            @RequestBody SearchReceivedMessageCondition searchReceivedMessageCondition, Pageable pageable,
            BindingResult result
                                          ) {

        if(userId.equals("anonymousUser")) {
            throw new MessageCustomException(MessageExceptionEnum.UNAUTHORIZED_ACCESS);
        }

        PageImpl<MessageReceivedResponseDTO> receiverList = messageService.getReceivedMessagePageListWithSearch(searchReceivedMessageCondition,pageable,userId);
        return ResponseEntity
                .ok()
                .body(receiverList);
    }
    //보낸 메세지 검색
    @PostMapping("/api/messages/search/sent")
    public ResponseEntity<?> getSentPageList(
            @AuthenticationPrincipal String userId,
            @RequestBody SearchSentMessageCondition searchSentMessageCondition, Pageable pageable
    ) {
        if(userId.equals("anonymousUser")) {
            throw new MessageCustomException(MessageExceptionEnum.UNAUTHORIZED_ACCESS);
        }
        if (searchSentMessageCondition.getMessageContent() ==null && searchSentMessageCondition.getMessageTitle()==null && searchSentMessageCondition.getMessageReceiver()==null){
            PageImpl<MessageSentResponseDTO> responseDTO = messageService.getSentMessagePageList(pageable,userId);
            return ResponseEntity
                    .ok()
                    .body(responseDTO);
        } else {
        PageImpl<MessageSentResponseDTO> sentList = messageService.getSentMessagePageListWithSearch(searchSentMessageCondition,pageable,userId);
        return ResponseEntity
                .ok()
                .body(sentList);
        }
    }


    // 받는 사람 실시간 검색
    @GetMapping("/api/messages/receiver")
    public ResponseEntity<?> findReceiver(@RequestParam("username") String userName,
                                          @AuthenticationPrincipal String userId) {

        if(userId.equals("anonymousUser")) {
            throw new MessageCustomException(MessageExceptionEnum.UNAUTHORIZED_ACCESS);
        }

        List<MessageReceiverResponseDTO> receiverList = messageService.findReceiver(userName);
        return ResponseEntity
                .ok()
                .body(receiverList);
    }

    // 메세지 보내기
    @PostMapping("/api/messages")
    public ResponseEntity<?> sendMessages(@Validated @RequestBody MessageSendRequestDTO message,
                                         @RequestParam("receiverList") List<String> receiverList,
                                          @AuthenticationPrincipal String userId) {

        boolean result = messageService.sendMessage(userId, receiverList, message);
        return ResponseEntity
                .ok()
                .body(result);

    }

    // 메세지 삭제
    @DeleteMapping("/api/messages")
    public ResponseEntity<?> deleteMessages(@RequestParam("messageId") List<Long> messageList,
                                            @AuthenticationPrincipal String userId) {

        if(userId.equals("anonymousUser")) {
            throw new MessageCustomException(MessageExceptionEnum.UNAUTHORIZED_ACCESS);
        }

        boolean result = messageService.deleteMessage(messageList, userId);
        return ResponseEntity
                .ok()
                .body(result);
    }

}
