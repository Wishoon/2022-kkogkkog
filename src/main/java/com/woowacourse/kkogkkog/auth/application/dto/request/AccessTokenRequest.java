package com.woowacourse.kkogkkog.auth.application.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class AccessTokenRequest {

    private String code;

    public AccessTokenRequest(final String code) {
        this.code = code;
    }
}
