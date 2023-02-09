package com.newcen.newcen.message.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum MessageExceptionEnum {

    INVALID_PARAMETER(HttpStatus.BAD_REQUEST, "400", "Invalid Parameter Values Or Duplicated Key Values"),
    MESSAGE_NOT_FOUND(HttpStatus.NOT_FOUND, "404", "Unable to Find Message By Given Message ID"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "500", "Internal Server Error Occurred")
    ;


    private final HttpStatus status;
    private final String code;
    private final String message;

    private MessageExceptionEnum(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

}
