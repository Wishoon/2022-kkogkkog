package com.woowacourse.kkogkkog.auth.infrastructure.token;

import org.springframework.stereotype.Component;

@Component
public class TokenExtractor {

    public String extractToken(final String authorizationHeader, final String tokenType) {
        if (authorizationHeader == null) {
            throw new IllegalArgumentException();
        }
        final String[] splitHeaders = authorizationHeader.split(" ");
        if (splitHeaders.length != 2 || !splitHeaders[0].equalsIgnoreCase(tokenType)) {
            throw new IllegalArgumentException();
        }
        return splitHeaders[1];
    }
}
