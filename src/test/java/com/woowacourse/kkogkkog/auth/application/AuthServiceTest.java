package com.woowacourse.kkogkkog.auth.application;

import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.kkogkkog.annotation.IntegrationTest;
import com.woowacourse.kkogkkog.auth.application.dto.response.AccessTokenResponse;
import com.woowacourse.kkogkkog.auth.infrastructure.client.dto.response.OAuthProfileResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@IntegrationTest
class AuthServiceTest {

    @Autowired
    private AuthService authService;

    @Test
    void OAuthProfile의_정보를_통해_AccessToken을_생성할_수_있다() {
        OAuthProfileResponse oAuthProfile = new OAuthProfileResponse(1L, "email@gmail.com", "ROOKIE", "https://avatars.githubusercontent.com/u/48710213?s=400&u=c14998dc373586afa6eed653ed8424ec310a47ef&v=4");

        AccessTokenResponse actual = authService.createAccessToken(oAuthProfile);

        assertThat(actual.getAccessToken()).isNotNull();
    }
}