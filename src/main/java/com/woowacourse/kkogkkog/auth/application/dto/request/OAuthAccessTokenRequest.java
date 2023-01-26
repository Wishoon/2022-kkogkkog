package com.woowacourse.kkogkkog.auth.application.dto.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class OAuthAccessTokenRequest {

    private String clientId;
    private String clientSecret;
    private String code;
}
