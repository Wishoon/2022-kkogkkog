package com.woowacourse.kkogkkog.auth.infrastructure.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.woowacourse.kkogkkog.auth.application.OAuthClient;
import com.woowacourse.kkogkkog.auth.infrastructure.client.dto.request.OAuthAccessTokenRequest;
import com.woowacourse.kkogkkog.auth.infrastructure.client.dto.response.OAuthAccessTokenResponse;
import com.woowacourse.kkogkkog.auth.infrastructure.client.dto.response.OAuthProfileResponse;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class GithubOAuthClient implements OAuthClient {

    private final String clientId;
    private final String clientSecret;
    private final String accessTokenURL;
    private final String profileURL;
    private final RestTemplate restTemplate;

    public GithubOAuthClient(@Value("${oauth2.github.client-id}") final String clientId,
                             @Value("${oauth2.github.client-secret}") final String clientSecret,
                             @Value("${oauth2.github.access-token-url}") final String accessTokenURL,
                             @Value("${oauth2.github.profile-url}") final String profileURL,
                             final RestTemplate restTemplate) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.accessTokenURL = accessTokenURL;
        this.profileURL = profileURL;
        this.restTemplate = restTemplate;
    }

    @Override
    public OAuthAccessTokenResponse getAccessToken(final String code) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);

        OAuthAccessTokenRequest request = new OAuthAccessTokenRequest(clientId, clientSecret, code);

        return invokeAccessToken(httpHeaders, request);
    }

    private OAuthAccessTokenResponse invokeAccessToken(final HttpHeaders httpHeaders,
                                                       final OAuthAccessTokenRequest request) {
        try {
            HttpEntity<?> requestEntity = new HttpEntity<>(request, httpHeaders);
            GithubAccessTokenResponse response = restTemplate.exchange(
                accessTokenURL, HttpMethod.POST, requestEntity, GithubAccessTokenResponse.class).getBody();
            return new OAuthAccessTokenResponse(response.getAccessToken());
        } catch (final Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public OAuthProfileResponse getProfile(final String accessToken) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        httpHeaders.add(HttpHeaders.AUTHORIZATION, "token " + accessToken);

        return invokeProfile(httpHeaders);
    }

    private OAuthProfileResponse invokeProfile(final HttpHeaders httpHeaders) {
        try {
            HttpEntity<?> requestEntity = new HttpEntity<>(httpHeaders);
            GithubProfileResponse response = restTemplate.exchange(
                profileURL, HttpMethod.POST, requestEntity, GithubProfileResponse.class).getBody();
            return new OAuthProfileResponse(
                response.getGithubId(), response.getEmail(), response.getUsername(), response.getImageUrl());
        } catch (final Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public static class GithubAccessTokenResponse {

        @JsonProperty("id_token")
        private String accessToken;
    }

    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public static class GithubProfileResponse {

        @JsonProperty("id")
        private Long githubId;

        @JsonProperty("login")
        private String username;

        @JsonProperty("email")
        private String email;

        @JsonProperty("avatar_url")
        private String imageUrl;
    }
}
