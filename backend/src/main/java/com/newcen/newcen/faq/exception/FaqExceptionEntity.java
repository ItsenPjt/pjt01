package com.newcen.newcen.faq.exception;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FaqExceptionEntity {

    private String errorCode;
    private String errorMessage;

}
