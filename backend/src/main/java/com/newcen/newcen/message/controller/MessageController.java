package com.newcen.newcen.message.controller;

import com.newcen.newcen.message.service.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class MessageController {

    private final MessageService mainService;

    @GetMapping("/api")
    public ResponseEntity<?> home() {




        return null;
    }

}
