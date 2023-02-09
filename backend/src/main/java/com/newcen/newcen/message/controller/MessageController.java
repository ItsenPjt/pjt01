package com.newcen.newcen.message.controller;

import com.newcen.newcen.message.dto.request.MessageSendRequestDTO;
import com.newcen.newcen.message.dto.response.*;
import com.newcen.newcen.message.exception.MessageExceptionEntity;
import com.newcen.newcen.message.exception.MessageExceptionEnum;
import com.newcen.newcen.message.service.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
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
    @GetMapping("/api/{userId}/messages")
    public ResponseEntity<?> messageList(@PathVariable("userId") String userId, @RequestParam("mode") String mode) {

        log.info("/api/{}/message message list GET request!", userId);

        if(mode.equals("received")) {
            MessageReceivedListResponseDTO messageList = messageService.receivedMessageList(userId);
            log.info("Received Message List Return Success😄");
            return ResponseEntity
                    .ok()
                    .body(messageList.getReceivedMessageList());
        }else if(mode.equals("sent")) {
            MessageSentListResponseDTO messageList = messageService.sentMessageList(userId);
            log.info("Received Message List Return Success😄");
            return ResponseEntity
                    .ok()
                    .body(messageList.getSentMessageList());
        }else {
            throw new InvalidParameterException();
        }

    }

    // 메세지 상세 조회
    @GetMapping("/api/{userId}/messages/{messageId}")
    public ResponseEntity<?> receivedMessageDetail(@PathVariable("userId") String userId,
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

    // 받는 사람 실시간 검색
    @GetMapping("/api/{userId}/messages/receiver")
    public ResponseEntity<?> findReceiver(@RequestParam("username") String userName) {

        List<MessageReceiverResponseDTO> receiverList = messageService.findReceiver(userName);
        return ResponseEntity
                .ok()
                .body(receiverList);
    }

    // 메세지 보내기
    @PostMapping("/api/{userId}/messages")
    public ResponseEntity<?> sendMessages(@Validated @RequestBody MessageSendRequestDTO message,
                                         @RequestParam("receiverList") List<String> receiverList,
                                         @PathVariable("userId") String userId) {

        MessageReceivedListResponseDTO receivedList = messageService.sendMessage(userId, receiverList, message);
        return ResponseEntity
                .ok()
                .body(receivedList);

    }

    // 메세지 삭제
    @DeleteMapping("/api/{userId}/messages")
    public ResponseEntity<?> deleteMessages(@RequestParam("messageId") List<Long> messageList,
                                            @PathVariable("userId") String userId) {

        MessageReceivedListResponseDTO receivedList = messageService.deleteMessage(messageList, userId);
        return ResponseEntity
                .ok()
                .body(receivedList);
    }

}
