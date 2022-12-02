package com.woowacourse.kkogkkog.support.fake;

import com.woowacourse.kkogkkog.auth.application.dto.request.OAuthAccessTokenRequest;
import com.woowacourse.kkogkkog.auth.infrastructure.client.GithubOAuthClient.GithubAccessTokenResponse;
import com.woowacourse.kkogkkog.auth.infrastructure.client.GithubOAuthClient.GithubProfileResponse;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Profile("test")
@RestController
@RequestMapping("/api/github")
public class FakeOAuthGithubController {

    private final Map<String, String> codeAndTokenMap = Map.of(
        "ROOKIE_OAUTH_CODE", "ROOKIE_OAUTH_ACCESS_TOKEN",
        "ROMA_OAUTH_CODE", "ROMA_OAUTH_ACCESS_TOKEN",
        "BROWN_OAUTH_CODE", "BROWN_OAUTH_ACCESS_TOKEN");

    private final Map<String, GithubProfileResponse> tokenAndMemberProfileMap = Map.of(
        "ROOKIE_OAUTH_ACCESS_TOKEN", new GithubProfileResponse(1L, "ROOKIE", "rookie@gmail.com",
            "https://avatars.githubusercontent.com/u/48710213?s=400&u=c14998dc373586afa6eed653ed8424ec310a47ef&v=4"),
        "ROMA_OAUTH_ACCESS_TOKEN", new GithubProfileResponse(2L, "ROMA", "roma@gmail.com",
            "https://avatars.githubusercontent.com/u/52696169?v=4"),
        "BROWN_OAUTH_ACCESS_TOKEN", new GithubProfileResponse(3L, "BROWN", "brown@gmail.com",
            "https://avatars.githubusercontent.com/u/4353846?v=4"));

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
    public ResponseEntity<GithubAccessTokenResponse> invokeFakeAccessToken(
        @RequestBody final OAuthAccessTokenRequest request) {
        if (!request.getClientId().equals(clientId) || !request.getClientSecret().equals(clientSecret)) {
            return ResponseEntity.badRequest().build();
        }

        final String accessToken = codeAndTokenMap.get(request.getCode());
        if (accessToken == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(new GithubAccessTokenResponse(accessToken));
    }

    /**
     * Github Fake Profile Token API
     */
    @PostMapping("/users")
    public ResponseEntity<GithubProfileResponse> invokeFakeProfile(
        @RequestHeader(value = HttpHeaders.AUTHORIZATION) final String authorizationHeaderValue) {
        if (authorizationHeaderValue == null) {
            return ResponseEntity.badRequest().build();
        }
        final String[] splitValue = authorizationHeaderValue.split(" ");
        if (splitValue.length != 2 || !splitValue[0].equals("token")) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(tokenAndMemberProfileMap.get(splitValue[1]));
    }
}
