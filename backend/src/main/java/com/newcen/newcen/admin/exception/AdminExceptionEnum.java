package com.newcen.newcen.admin.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum AdminExceptionEnum {

    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "500", "Internal Server Error Occurred"),
    INVALID_PARAMETER(HttpStatus.BAD_REQUEST, "400", "Invalid Parameters or Duplicate Keys"),
    ACCESS_FORBIDDEN(HttpStatus.FORBIDDEN, "403", "Access Denied"),
    TOKEN_REQUIRED(HttpStatus.FORBIDDEN, "403", "Token Is Required"),
    USER_NOT_EXIST(HttpStatus.NOT_FOUND, "404", "User Does Not Exist With Given ID"),
    EMPTY_LIST(HttpStatus.NOT_FOUND, "404", "User List Is Empty"),
    NOT_SUPPORTED_METHOD(HttpStatus.METHOD_NOT_ALLOWED, "405", "HTTP Method NOT Allowed")
    ;


    private final HttpStatus status;
    private final String code;
    private final String message;

    private AdminExceptionEnum(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }


}
