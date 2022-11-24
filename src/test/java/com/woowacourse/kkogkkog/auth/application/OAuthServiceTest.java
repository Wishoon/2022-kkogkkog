package com.woowacourse.kkogkkog.auth.application;

import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.kkogkkog.annotation.IntegrationTest;
import com.woowacourse.kkogkkog.auth.application.dto.request.AccessTokenRequest;
import com.woowacourse.kkogkkog.auth.infrastructure.client.dto.response.OAuthProfileResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@IntegrationTest
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
class OAuthServiceTest {

    @Autowired
    private OAuthService oAuthService;

    @Test
    void OAuthClient의_이름과_OAuth_인증_코드를_통해_OAuth_프로필을_조회할_수_있다() {
        OAuthProfileResponse actual = oAuthService.getOauthProfile(
            "github", new AccessTokenRequest("ROOKIE_OAUTH_CODE"));

        assertThat(actual).isInstanceOf(OAuthProfileResponse.class);
    }
}
