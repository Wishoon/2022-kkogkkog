package com.woowacourse.kkogkkog.quantity.application;

import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.kkogkkog.annotation.IntegrationTest;
import com.woowacourse.kkogkkog.member.domain.Member;
import com.woowacourse.kkogkkog.member.domain.MemberRepository;
import com.woowacourse.kkogkkog.member.domain.ProviderType;
import com.woowacourse.kkogkkog.quantity.application.dto.QuantityCouponCreateRequest;
import com.woowacourse.kkogkkog.quantity.entity.repository.QuantityCouponRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@IntegrationTest
class QuantityCouponServiceTest {

    @Autowired
    private QuantityCouponService quantityCouponService;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private QuantityCouponRepository quantityCouponRepository;

    @Test
    void 수량_쿠폰을_생성할_수_있다() {
        Long memberId = memberRepository.save(createMember("oauth-루키-id", "루키@gmail.com", "루키")).getId();

        quantityCouponService.create(memberId, new QuantityCouponCreateRequest("수량 쿠폰에 담을 내용", "수량 쿠폰의 카테고리", 10));

        assertThat(1).isEqualTo(quantityCouponRepository.findAll().size());
    }

    private static Member createMember(final String providerId, final String email, final String username) {
        return Member.builder()
            .providerId(providerId)
            .email(email)
            .username(username)
            .imageUrl("https://image.com")
            .providerType(ProviderType.GITHUB).build();
    }
}
