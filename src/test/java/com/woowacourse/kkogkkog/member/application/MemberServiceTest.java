package com.woowacourse.kkogkkog.member.application;

import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.kkogkkog.annotation.IntegrationTest;
import com.woowacourse.kkogkkog.member.application.dto.MemberCreateOrUpdateRequest;
import com.woowacourse.kkogkkog.member.domain.Member;
import com.woowacourse.kkogkkog.member.domain.MemberRepository;
import com.woowacourse.kkogkkog.member.domain.ProviderType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@IntegrationTest
class MemberServiceTest {

    @Autowired
    private MemberService memberService;
    @Autowired
    private MemberRepository memberRepository;

    @Test
    void 회원의_정보가_존재하지_않는_경우_회원을_생성한다() {
        memberRepository.save(new Member(
            null, "oauthId-1", "email@gmail.com", "username", "https://profile.com", ProviderType.GITHUB, false));
        MemberCreateOrUpdateRequest request = new MemberCreateOrUpdateRequest(
            "oauthId-1", "email@gmail.com", "username", "https://profile.com", "github");

        memberService.createOrUpdate(request);

        assertThat(memberRepository.findAll()).hasSize(1);
    }

    @Test
    void 회원의_정보가_존재하는_경우_회원정보를_변경한다() {
        MemberCreateOrUpdateRequest request = new MemberCreateOrUpdateRequest(
            "oauthId-1", "email@gmail.com", "username", "https://profile.com", "github");

        memberService.createOrUpdate(request);

        assertThat(memberRepository.findAll()).hasSize(1);
    }
}
