package com.woowacourse.kkogkkog.auth.infrastructure.client;

import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.kkogkkog.auth.application.InMemoryClientRepository;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

class InMemoryClientRepositoryTest {

    @Test
    void OAuthClient_목록들을_관리할_수_있다() {
        InMemoryClientRepository actual = new InMemoryClientRepository(
            Map.of("GithubOAuthClient", new GithubOAuthClient("id", "secret", "url", new RestTemplate()))
        );

        assertThat(actual.findByClientName("GithubOAuthClient")).isInstanceOf(GithubOAuthClient.class);
    }
}