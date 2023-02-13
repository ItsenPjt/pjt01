package com.newcen.newcen.faq.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum FaqExceptionEnum {

    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "500", "Internal Server Error Occurred"),
    INVALID_PARAMETER(HttpStatus.BAD_REQUEST, "400", "Invalid Parameters"),
    ACCESS_FORBIDDEN(HttpStatus.FORBIDDEN, "403", "Access Denied"),
    INVALID_FAQ_ID(HttpStatus.NOT_FOUND, "404", "FAQ Does Not Exist With Given ID"),
    USER_NOT_EXIST(HttpStatus.NOT_FOUND, "404", "User Does Not Exist With Given ID"),
    EMPTY_LIST(HttpStatus.NOT_FOUND, "404", "FAQ List Is Empty"),
    ;


    private final HttpStatus status;
    private final String code;
    private final String message;

    private FaqExceptionEnum(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }


}
