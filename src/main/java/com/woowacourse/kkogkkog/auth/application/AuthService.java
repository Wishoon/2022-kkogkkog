package com.woowacourse.kkogkkog.auth.application;

import com.woowacourse.kkogkkog.auth.application.dto.response.AccessTokenResponse;
import com.woowacourse.kkogkkog.auth.infrastructure.client.dto.response.OAuthProfileResponse;
import com.woowacourse.kkogkkog.auth.infrastructure.token.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class AuthService {

    private final TokenProvider tokenProvider;

    public AccessTokenResponse createAccessToken(final OAuthProfileResponse oauthProfile) {
        /**
         * Member가 존재하는지 여부를 추후 확인해야 함
         * Member가 이미 존재할 경우 Update 처리를 해줘야 함
         * */
        String accessToken = tokenProvider.createAccessToken(oauthProfile.getId());

        return new AccessTokenResponse(accessToken);
    }
}
