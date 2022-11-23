package com.woowacourse.kkogkkog.auth.infrastructure.client.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class OAuthAccessTokenRequest {

    private String clientId;
    private String clientSecret;
    private String code;

    public OAuthAccessTokenRequest(final String clientId, final String clientSecret, final String code) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.code = code;
    }
}
