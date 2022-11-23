package com.woowacourse.kkogkkog.auth.application;

import java.util.Map;
import java.util.Map.Entry;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Getter
@Component
public class InMemoryClientRepository {

    private final Map<String, OAuthClient> clients;

    public InMemoryClientRepository(final Map<String, OAuthClient> clients) {
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
