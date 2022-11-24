package com.woowacourse.kkogkkog.auth.infrastructure.token;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class TokenProviderTest {

    private final TokenProvider tokenProvider = new TokenProvider(new TokenExtractor(),
        "validToken-afefklmaefalefnaelfnalfkenaefaelfnaekfewa",
        3600000);

    @Test
    void 회원_아이디를_통해_토큰을_생성할_수_있다() {
        Long memberId = 1L;

        String token = tokenProvider.createAccessToken(memberId);

        assertThat(token).isNotNull();
    }

    @Test
    void 발급된_토큰의_유효성_검사를_할_수_있다() {
        String token = tokenProvider.createAccessToken(1L);
        String authorizationHeader = "Bearer " + token;

        assertThat(tokenProvider.isValidToken(authorizationHeader)).isTrue();
    }

    @Test
    void 발급된_토큰이_만료된_경우_예외가_발생한다() {
        TokenProvider jwtProvider = new TokenProvider(new TokenExtractor(),
            "invalidToken-expiration-date-akflmelaflekfnaklenfea",
            0);
        String token = jwtProvider.createAccessToken(1L);
        String authorizationHeader = "Bearer " + token;

        assertThat(jwtProvider.isValidToken(authorizationHeader)).isFalse();
    }

    @Test
    void 토큰의_형식이_틀린_경우_예외가_발생한다() {
        String authorizationHeader = "Bearer invalidToken";

        assertThat(tokenProvider.isValidToken(authorizationHeader)).isFalse();
    }

    @Test
    void 토큰의_비밀키가_잘못된_경우_예외가_발생한다() {
        TokenProvider invalidJwtProvider = new TokenProvider(new TokenExtractor(),
            "invalidToken-wrong-key-afkleanflkeaflaefaneflnaenf",
            10000000);
        String token = invalidJwtProvider.createAccessToken(1L);
        String authorizationHeader = "Bearer " + token;

        assertThat(tokenProvider.isValidToken(authorizationHeader)).isFalse();
    }
}
