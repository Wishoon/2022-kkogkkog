package com.woowacourse.kkogkkog.auth.repository;

import com.woowacourse.kkogkkog.auth.application.OAuthClient;
import java.util.Map;
import java.util.Map.Entry;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Getter
@Component
public class InMemoryOAuthClientRepository {

    private final Map<String, OAuthClient> clients;

    public InMemoryOAuthClientRepository(final Map<String, OAuthClient> clients) {
        this.clients = clients;
    }

    public OAuthClient findByClientName(final String oauthClientName) {
        return clients.entrySet().stream()
            .filter(entry -> entry.getKey().contains(oauthClientName))
            .map(Entry::getValue)
            .findAny()
            .orElseThrow(IllegalArgumentException::new);
    }
}
