package com.woowacourse.kkogkkog.member.application;

import static com.woowacourse.kkogkkog.support.fixture.MemberFixture.인증된_회원;
import static com.woowacourse.kkogkkog.support.fixture.MemberFixture.회원;
import static com.woowacourse.kkogkkog.support.fixture.MemberFixture.회원을_생성하거나_변경하는_요청;
import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.kkogkkog.annotation.IntegrationTest;
import com.woowacourse.kkogkkog.member.application.dto.MemberCreateOrUpdateRequest;
import com.woowacourse.kkogkkog.member.domain.MemberRepository;
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
        memberRepository.save(회원());

        MemberCreateOrUpdateRequest request = 회원을_생성하거나_변경하는_요청();
        memberService.createOrUpdate(request);

        assertThat(memberRepository.findAll()).hasSize(1);
    }

    @Test
    void 회원의_정보가_존재하는_경우_회원정보를_변경한다() {
        MemberCreateOrUpdateRequest request = 회원을_생성하거나_변경하는_요청();

        memberService.createOrUpdate(request);

        assertThat(memberRepository.findAll()).hasSize(1);
    }

    @Test
    void 회원_ID를_통헤_인가여부를_조회할_수_있다() {
        Long memberId = memberRepository.save(인증된_회원()).getId();

        boolean actual = memberService.findApproval(memberId);

        assertThat(actual).isTrue();
    }
}
