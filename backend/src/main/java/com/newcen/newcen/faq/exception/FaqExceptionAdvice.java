package com.newcen.newcen.faq.exception;

import com.newcen.newcen.message.exception.MessageCustomException;
import com.newcen.newcen.message.exception.MessageExceptionEntity;
import com.newcen.newcen.message.exception.MessageExceptionEnum;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpServerErrorException;

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
