package com.woowacourse.kkogkkog.coupon.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.kkogkkog.annotation.IntegrationTest;
import com.woowacourse.kkogkkog.coupon.application.dto.response.CouponResponse;
import com.woowacourse.kkogkkog.coupon.domain.Category;
import com.woowacourse.kkogkkog.coupon.domain.Condition;
import com.woowacourse.kkogkkog.coupon.domain.Coupon;
import com.woowacourse.kkogkkog.coupon.domain.repository.CouponRepository;
import com.woowacourse.kkogkkog.member.domain.Member;
import com.woowacourse.kkogkkog.member.domain.MemberRepository;
import com.woowacourse.kkogkkog.member.domain.ProviderType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;

@IntegrationTest
class CouponQueryServiceTest {

    @Autowired
    private CouponQueryService couponQueryService;
    @Autowired
    private CouponRepository couponRepository;
    @Autowired
    private MemberRepository memberRepository;

    @Test
    void 쿠폰을_조회할_수_있다() {
        Long senderId = memberRepository.save(createMember("oauth-루키-id", "루키@gmail.com", "루키")).getId();
        Long receiverId = memberRepository.save(createMember("oauth-키루-id", "키루@gmail.com", "키루")).getId();
        Long couponId = couponRepository.save(createCoupon(senderId, receiverId)).getId();

        CouponResponse actual = couponQueryService.find(couponId);

        assertThat(actual.getCouponId()).isEqualTo(1L);
    }

    // CustomException 기능 제작 전까지 InvalidDataAccessApiUsageException 을 사용
    @Test
    void 쿠폰을_조회할_때_존재하지_않는_쿠폰_ID로_조회하는_경우_예외가_발생한다() {
        memberRepository.save(createMember("oauth-루키-id", "루키@gmail.com", "루키")).getId();
        memberRepository.save(createMember("oauth-키루-id", "키루@gmail.com", "키루")).getId();

        assertThatThrownBy(() -> couponQueryService.find(999L))
            .isInstanceOf(InvalidDataAccessApiUsageException.class);
    }

    private static Member createMember(final String providerId, final String email, final String username) {
        return Member.builder()
            .providerId(providerId)
            .email(email)
            .username(username)
            .imageUrl("https://image.com")
            .providerType(ProviderType.GITHUB).build();
    }

    private static Coupon createCoupon(final Long senderId, final Long receiverId) {
        return Coupon.builder()
            .senderId(senderId)
            .receiverId(receiverId)
            .content("쿠폰의 내용")
            .category(Category.COFFEE)
            .condition(Condition.READY)
            .build();
    }
}
