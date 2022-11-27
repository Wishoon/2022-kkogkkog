package com.woowacourse.kkogkkog.common.exception;

import lombok.Getter;

@Getter
public enum ErrorType {

    AUTH_INVALID_TOKEN(1001, "토큰이 유효하지 않습니다."),
    AUTH_NOT_AUTHENTICATED(1002, "인증 정보가 존재하지 않습니다."),
    AUTH_FORBIDDEN(1003, "접근 권한이 없습니다."),

    COMMON_INVALID_PATH(9001, "요청 경로가 유효하지 않습니다."),
    COMMON_INVALID_HTTP(9002, "http 요청이 유효하지 않습니다."),
    COMMON_UNHANDLED(9999, "예상하지 못한 예외가 발생했습니다.");

    private final int code;
    private final String message;

    ErrorType(final int code, final String message) {
        this.code = code;
        this.message = message;
    }
}
