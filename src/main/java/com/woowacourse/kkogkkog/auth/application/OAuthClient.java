package com.woowacourse.kkogkkog.auth.application;

import com.woowacourse.kkogkkog.auth.infrastructure.client.dto.response.OAuthAccessTokenResponse;
import com.woowacourse.kkogkkog.auth.infrastructure.client.dto.response.OAuthProfileResponse;

public interface OAuthClient {

    OAuthAccessTokenResponse getAccessToken(final String code);

    OAuthProfileResponse getProfile(final String accessToken);
}
