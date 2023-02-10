package com.newcen.newcen.admin.exception;

import lombok.Getter;

@Getter
public class AdminCustomException extends RuntimeException {

    private final AdminExceptionEnum faqExceptionEnum;

    public AdminCustomException(AdminExceptionEnum faqExceptionEnum) {
        this.faqExceptionEnum = faqExceptionEnum;
    }
}
