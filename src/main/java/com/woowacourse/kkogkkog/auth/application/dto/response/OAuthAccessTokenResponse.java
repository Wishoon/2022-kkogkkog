package com.woowacourse.kkogkkog.auth.application.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class OAuthAccessTokenResponse {

    private String accessToken;

    public OAuthAccessTokenResponse(String accessToken) {
        this.accessToken = accessToken;
    }
}
