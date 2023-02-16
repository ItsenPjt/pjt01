package com.newcen.newcen.admin.service;

import com.newcen.newcen.admin.dto.SendEmailDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    public void sendSimpleMessage(SendEmailDTO emailDTO) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("ITCEN@gmail.com");
        message.setTo(emailDTO.getAddress());
        message.setSubject(emailDTO.getTitle());
        message.setText(emailDTO.getContent());
        mailSender.send(message);

    }

}
