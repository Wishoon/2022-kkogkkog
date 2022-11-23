package com.woowacourse.kkogkkog.auth.application;

import com.woowacourse.kkogkkog.auth.application.dto.request.AccessTokenRequest;
import com.woowacourse.kkogkkog.auth.infrastructure.client.dto.response.OAuthAccessTokenResponse;
import com.woowacourse.kkogkkog.auth.infrastructure.client.dto.response.OAuthProfileResponse;
import com.woowacourse.kkogkkog.auth.repository.InMemoryClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OAuthService {

    private final InMemoryClientRepository inMemoryProviderRepository;

    public OAuthProfileResponse getOauthProfile(final String oauthClientName,
                                                final AccessTokenRequest request) {
        OAuthClient oAuthClient = inMemoryProviderRepository.findByClientName(oauthClientName);
        OAuthAccessTokenResponse oAuthAccessTokenResponse = oAuthClient.getAccessToken(request.getCode());

        return oAuthClient.getProfile(oAuthAccessTokenResponse.getAccessToken());
    }
}
