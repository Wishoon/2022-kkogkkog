package com.woowacourse.kkogkkog.member.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.Test;

class MemberTest {

    @Test
    void 회원의_정보를_수정할_수_있다() {
        Member member = new Member(null, "oauthId-1", "email@gmail.com", "username", "https://imageUrl.com",
            ProviderType.GITHUB, false);

        Member actual = member.update("username_change", "https://imageUrl-change.com");

        assertAll(
            () -> assertThat(actual.getUsername()).isEqualTo("username_change"),
            () -> assertThat(actual.getImageUrl()).isEqualTo("https://imageUrl-change.com"));
    }
}
