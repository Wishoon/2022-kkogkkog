package com.woowacourse.kkogkkog.support.fake;

import com.woowacourse.kkogkkog.auth.infrastructure.client.GithubOAuthClient.GithubAccessTokenResponse;
import com.woowacourse.kkogkkog.auth.infrastructure.client.dto.request.OAuthAccessTokenRequest;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Profile("test")
@RestController
public class FakeOAuthGithubController {

    private final Map<String, String> accessTokens = Map.of(
        "ROOKIE_OAUTH_CODE", "ROOKIE_OAUTH_ACCESS_TOKEN");

    private final String clientId;
    private final String clientSecret;

    public FakeOAuthGithubController(@Value("${oauth2.github.client-id}") final String clientId,
                                     @Value("${oauth2.github.client-secret}") final String clientSecret) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }

    /**
     * Github Fake Access Token API
     */
    @PostMapping("/login/oauth/access_token")
    public ResponseEntity<GithubAccessTokenResponse> invokeFakeAccessToken(@RequestBody final OAuthAccessTokenRequest request) {
        if (!request.getClientId().equals(clientId) || !request.getClientSecret().equals(clientSecret)) {
            return ResponseEntity.badRequest().build();
        }

        final String accessToken = accessTokens.get(request.getCode());
        if (accessToken == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(new GithubAccessTokenResponse(accessToken));
    }
}
