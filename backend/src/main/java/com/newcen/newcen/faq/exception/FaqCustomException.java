package com.newcen.newcen.faq.exception;

import com.newcen.newcen.message.exception.MessageExceptionEnum;
import lombok.Getter;

@Getter
public class FaqCustomException extends RuntimeException {

    private final FaqExceptionEnum faqExceptionEnum;

    public FaqCustomException(FaqExceptionEnum faqExceptionEnum) {
        this.faqExceptionEnum = faqExceptionEnum;
    }
}
