package com.newcen.newcen.message.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.aspectj.bridge.Message;
import org.springframework.beans.factory.annotation.Required;

import javax.swing.*;
@Getter
@RequiredArgsConstructor
public class MessageCustomException extends RuntimeException {

    private final MessageExceptionEnum messageExceptionEnum;



}
