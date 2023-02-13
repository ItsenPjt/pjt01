package com.newcen.newcen.faq.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.newcen.newcen.message.exception.MessageCustomException;
import com.newcen.newcen.message.exception.MessageExceptionEntity;
import com.newcen.newcen.message.exception.MessageExceptionEnum;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import java.net.BindException;

@RestControllerAdvice(basePackages = "com.newcen.newcen.faq")
public class FaqExceptionAdvice {

    @ExceptionHandler({HttpServerErrorException.InternalServerError.class})
    public ResponseEntity<?> internalServerErrorHandler(Exception e) {
        return ResponseEntity
                .status(FaqExceptionEnum.INTERNAL_SERVER_ERROR.getStatus())
                .body(FaqExceptionEntity.builder()
                        .errorCode(FaqExceptionEnum.INTERNAL_SERVER_ERROR.getCode())
                        .errorMessage(FaqExceptionEnum.INTERNAL_SERVER_ERROR.getMessage())
                        .build()
                );
    }

    @ExceptionHandler({
            MissingPathVariableException.class,
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
        return ResponseEntity.status(FaqExceptionEnum.INVALID_PARAMETER.getStatus())
                .body(FaqExceptionEntity.builder()
                        .errorCode(FaqExceptionEnum.INVALID_PARAMETER.getCode())
                        .errorMessage(FaqExceptionEnum.INVALID_PARAMETER.getMessage())
                        .build()
                );
    }


    @ExceptionHandler({FaqCustomException.class})
    public ResponseEntity<?> faqCustomExceptionHandler(FaqCustomException ce) {
        return ResponseEntity
                .status(ce.getFaqExceptionEnum().getStatus())
                .body(FaqExceptionEntity.builder()
                        .errorCode(ce.getFaqExceptionEnum().getCode())
                        .errorMessage(ce.getFaqExceptionEnum().getMessage())
                        .build()
                );
    }


}
