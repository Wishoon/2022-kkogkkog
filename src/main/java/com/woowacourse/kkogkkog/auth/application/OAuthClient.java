package com.woowacourse.kkogkkog.auth.application;

import com.woowacourse.kkogkkog.auth.infrastructure.client.dto.response.OAuthAccessTokenResponse;

public interface OAuthClient {

    OAuthAccessTokenResponse getAccessToken(final String code);
}
