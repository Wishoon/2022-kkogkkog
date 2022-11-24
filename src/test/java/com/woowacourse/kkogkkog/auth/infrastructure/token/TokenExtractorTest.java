package com.woowacourse.kkogkkog.auth.infrastructure.token;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class TokenExtractorTest {

    private final TokenExtractor tokenExtractor = new TokenExtractor();

    @Test
    void Authorization_헤더에서_토큰을_추출할_수_있다() {
        String authorizationHeader = "Bearer token";

        String token = tokenExtractor.extractToken(authorizationHeader, "Bearer");

        assertThat(token).isEqualTo("token");
    }

    @Test
    void Authorization_헤더가_null이면_예외가_발생한다() {
        assertThatThrownBy(() -> tokenExtractor.extractToken(null, "Bearer"))
            .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void Authorization_헤더의_타입이_불일치하면_예외가_발생한다() {
        String authorizationHeader = "NotBearer token";

        assertThatThrownBy(() -> tokenExtractor.extractToken(authorizationHeader, "Bearer"))
            .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void Authorization_헤더의_형식이_불일치하면_예외가_발생한다() {
        String authorizationHeader = "Bearer token token";

        assertThatThrownBy(() -> tokenExtractor.extractToken(authorizationHeader, "Bearer"))
            .isExactlyInstanceOf(IllegalArgumentException.class);
    }
}
