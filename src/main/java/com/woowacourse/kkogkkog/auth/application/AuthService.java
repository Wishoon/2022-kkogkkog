package com.woowacourse.kkogkkog.auth.application;

import com.woowacourse.kkogkkog.auth.application.dto.response.AccessTokenResponse;
import com.woowacourse.kkogkkog.auth.infrastructure.client.dto.response.OAuthProfileResponse;
import com.woowacourse.kkogkkog.auth.infrastructure.token.TokenProvider;
import com.woowacourse.kkogkkog.member.application.MemberService;
import com.woowacourse.kkogkkog.member.application.dto.MemberCreateOrUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class AuthService {

    private final TokenProvider tokenProvider;
    private final MemberService memberService;

    @Transactional
    public AccessTokenResponse createAccessToken(final String oauthProvider, final OAuthProfileResponse oauthProfile) {
        Long memberId = memberService.createOrUpdate(
            new MemberCreateOrUpdateRequest(oauthProfile.getId(), oauthProfile.getEmail(),
                oauthProfile.getUsername(), oauthProfile.getProfileUrl(), oauthProvider));

        String accessToken = tokenProvider.createAccessToken(memberId);

        return new AccessTokenResponse(accessToken);
    }
}
