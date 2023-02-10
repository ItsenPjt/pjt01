package com.newcen.newcen.admin.exception;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AdminExceptionEntity {

    private String errorCode;
    private String errorMessage;

}
