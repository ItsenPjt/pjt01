package com.newcen.newcen.message.exception;


import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import java.net.BindException;

@RestControllerAdvice(basePackages = "com.newcen.newcen.message")
public class MessageExceptionAdvice {


    @ExceptionHandler({HttpServerErrorException.InternalServerError.class})
    public ResponseEntity<?> internalServerErrorHandler(Exception e) {
        return ResponseEntity
                .status(MessageExceptionEnum.INTERNAL_SERVER_ERROR.getStatus())
                .body(MessageExceptionEntity.builder()
                        .errorCode(MessageExceptionEnum.INTERNAL_SERVER_ERROR.getCode())
                        .errorMessage(MessageExceptionEnum.INTERNAL_SERVER_ERROR.getMessage())
                        .build()
                );
    }

    @ExceptionHandler({
            MethodArgumentNotValidException.class,
            MissingServletRequestParameterException.class,
            HttpClientErrorException.BadRequest.class,
            InvalidFormatException.class,
            DuplicateKeyException.class,
            IllegalArgumentException.class,
            DataIntegrityViolationException.class,
            HttpMessageNotReadableException.class,
            BindException.class
    })
    public ResponseEntity<?> invalidParameterHandler(Exception e) {
        return ResponseEntity.status(MessageExceptionEnum.INVALID_PARAMETER.getStatus())
                .body(MessageExceptionEntity.builder()
                        .errorCode(MessageExceptionEnum.INVALID_PARAMETER.getCode())
                        .errorMessage(MessageExceptionEnum.INVALID_PARAMETER.getMessage())
                        .build()
                );
    }

    @ExceptionHandler({MessageCustomException.class})
    public ResponseEntity<?> messageCustomExceptionHandler(MessageCustomException ce) {
        return ResponseEntity
                .status(ce.getMessageExceptionEnum().getStatus())
                .body(MessageExceptionEntity.builder()
                        .errorCode(ce.getMessageExceptionEnum().getCode())
                        .errorMessage(ce.getMessageExceptionEnum().getMessage())
                        .build()
                );
    }


}
