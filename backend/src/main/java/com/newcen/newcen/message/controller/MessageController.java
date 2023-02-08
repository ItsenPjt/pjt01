package com.newcen.newcen.message.controller;

import com.newcen.newcen.message.dto.request.MessageSendRequestDTO;
import com.newcen.newcen.message.dto.response.*;
import com.newcen.newcen.message.service.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
        if(userId == null || userId.trim().length() == 0 || mode==null || mode.trim().length() == 0) {
            // CLIENT ERROR
        }

        try {
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
            }
        }catch(Exception e) {
            // SERVER ERROR
        }
        return null;
    }

    // 메세지 상세 조회
    @GetMapping("/api/{userId}/messages/{messageId}")
    public ResponseEntity<?> receivedMessageDetail(@PathVariable("userId") String userId,
                                                   @PathVariable("messageId") Long messageId,
                                                   @RequestParam("mode") String mode) {

        log.info("/api/{}/message/{} message detail GET request!", userId, messageId);

        if(userId == null || userId.trim().length() == 0 || messageId == null || mode == null || mode.trim().length() == 0) {
            // CLIENT ERROR
        }

        try {
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
            }

        }catch (Exception e) {
            // SERVER ERROR
        }

        return  null;
    }

    // 받는 사람 실시간 검색
    @GetMapping("/api/{userId}/messages/receiver")
    public ResponseEntity<?> findReceiver(@RequestParam("userName") String userName) {
        if(userName == null || userName.trim().length() == 0) {
            // CLIENT ERROR
        }

        try {
            List<MessageReceiverResponseDTO> receiverList = messageService.findReceiver(userName);
            return ResponseEntity
                    .ok()
                    .body(receiverList);
        }catch (Exception e) {
            // SERVER ERROR
        }

        return null;
    }

    // 메세지 보내기
    @PostMapping("/api/{userId}/messages")
    public ResponseEntity<?> sendMessages(@Validated @RequestBody MessageSendRequestDTO message,
                                         @RequestParam("receiverList") List<String> receiverList,
                                         @PathVariable("userId") String userId,
                                         BindingResult result) {

        if(result.hasErrors()) {
            log.warn("Validation Failed for Message Send Request");
            // REQUESTDTO VALIDATION FAILED ERROR
        }

        if(receiverList.size()==0) {
            log.warn("Receiver Does Not Exist");
            // NO RECEIVER ERROR
        }

        if(userId == null || userId.trim().length() == 0) {
            log.warn("UserId is required path variable😡");
            // no path variable error
        }

        try {
            MessageReceivedListResponseDTO receivedList = messageService.sendMessage(userId, receiverList, message);
            return ResponseEntity
                    .ok()
                    .body(receivedList);
        }catch (Exception e) {
            // SERVER ERROR
        }

        return null;
    }

    // 메세지 삭제
    @DeleteMapping("/api/{userId}/messages")
    public ResponseEntity<?> deleteMessages(@RequestParam List<Long> messageList,
                                            @PathVariable("userId") String userId) {

        if(messageList.size()==0) {
            log.warn("Message Id is Required!");
            // Missing Message Id Error
        }

        if(userId == null || userId.trim().length() == 0) {
            log.warn("UserId is required path variable😡");
            // no path variable error
        }

        try {
            MessageReceivedListResponseDTO receivedList = messageService.deleteMessage(messageList, userId);
            return ResponseEntity
                    .ok()
                    .body(receivedList);
        }catch (Exception e) {
            // SERVER ERROR
        }


        return null;
    }




}
