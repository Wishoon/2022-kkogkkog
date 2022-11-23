package com.woowacourse.kkogkkog.auth.infrastructure.client;

import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.kkogkkog.annotation.IntegrationTest;
import com.woowacourse.kkogkkog.auth.infrastructure.client.dto.response.OAuthAccessTokenResponse;
import com.woowacourse.kkogkkog.auth.infrastructure.client.dto.response.OAuthProfileResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@IntegrationTest
// DEFINE_PORT가 필요한 관계로 다른 Context를 띄움
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
class GithubOAuthClientTest {

    @Autowired
    private GithubOAuthClient githubOAuthClient;

    @Test
    void OAuth_서버에서_받은_코드를_통해서_OAuthAccessToken을_받을_수_있다() {
        OAuthAccessTokenResponse actual = githubOAuthClient.getAccessToken("ROOKIE_OAUTH_CODE");

        assertThat(actual.getAccessToken()).isEqualTo("ROOKIE_OAUTH_ACCESS_TOKEN");
    }

    @Test
    void OAuth_서버에서_받은_AccessToken을_통해서_OAuthProfile을_받을_수_있다() {
        OAuthProfileResponse actual = githubOAuthClient.getProfile("ACCESS_TOKEN");

        assertThat(actual).isInstanceOf(OAuthProfileResponse.class);
    }
}