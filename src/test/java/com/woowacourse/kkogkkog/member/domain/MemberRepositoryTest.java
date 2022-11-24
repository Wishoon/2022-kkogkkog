package com.woowacourse.kkogkkog.member.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.kkogkkog.annotation.RepositoryTest;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@RepositoryTest
class MemberRepositoryTest {

    @Autowired
    private EntityManager entityManager;
    @Autowired
    private MemberRepository memberRepository;

    @Test
    void 회원의_정보를_이메일과_OAuth_제공자를_통해서_찾을_수_있다() {
        memberRepository.save(createMember());
        entityManager.clear();

        Member actual = memberRepository.findByEmailAndOauthProviderType("email@gmail.com",
            ProviderType.findProvider("github")).get();

        assertThat(actual).usingRecursiveComparison()
            .ignoringFields("id")
            .ignoringFields("createdTime")
            .ignoringFields("modifiedTime")
            .isEqualTo(createMember());
    }

    private static Member createMember() {
        return Member.builder()
            .providerId("oAuthId-1")
            .email("email@gmail.com")
            .username("username")
            .imageUrl("https://imageUrl.com")
            .providerType(ProviderType.GITHUB)
            .build();
    }
}
