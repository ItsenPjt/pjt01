package com.newcen.newcen.users.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor  // 파라미터가 없는 기본 생성자를 생성
public class DuplicatedEmailException extends RuntimeException {

    // 기본 생성자 @NoArgsConstructor 로 생성

    // 에러 메세지를 처리하는 생성자
    public DuplicatedEmailException(String message) {

        super(message);

    }

}
