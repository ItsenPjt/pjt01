package com.newcen.newcen.message.exception;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MessageExceptionEntity {

    private String errorCode;
    private String errorMessage;


}
