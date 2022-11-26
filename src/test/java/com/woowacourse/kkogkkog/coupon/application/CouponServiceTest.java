package com.woowacourse.kkogkkog.coupon.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.kkogkkog.annotation.IntegrationTest;
import com.woowacourse.kkogkkog.coupon.application.dto.request.CouponConditionUpdateRequest;
import com.woowacourse.kkogkkog.coupon.application.dto.request.CouponCreateRequest;
import com.woowacourse.kkogkkog.coupon.domain.Category;
import com.woowacourse.kkogkkog.coupon.domain.Condition;
import com.woowacourse.kkogkkog.coupon.domain.Coupon;
import com.woowacourse.kkogkkog.coupon.domain.repository.CouponRepository;
import com.woowacourse.kkogkkog.member.domain.Member;
import com.woowacourse.kkogkkog.member.domain.MemberRepository;
import com.woowacourse.kkogkkog.member.domain.ProviderType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@IntegrationTest
class CouponServiceTest {

    @Autowired
    private CouponService couponService;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private CouponRepository couponRepository;

    @Test
    void 쿠폰을_생성할_수_있다() {
        Long senderId = memberRepository.save(createMember("oauth-루키-id", "루키@gmail.com", "루키")).getId();
        Long receiverId = memberRepository.save(createMember("oauth-키루-id", "키루@gmail.com", "키루")).getId();

        CouponCreateRequest request = new CouponCreateRequest(senderId, receiverId, "쿠폰의 내용",
            Category.COFFEE.getValue());

        Long actual = couponService.create(request);
        assertThat(actual).isNotNull();
    }

    @Test
    void 쿠폰을_생성에서_존재하지_않는_회원이_포함된_경우_예외가_발생한다() {
        Long senderId = memberRepository.save(createMember("oauth-루키-id", "루키@gmail.com", "루키")).getId();

        CouponCreateRequest request = new CouponCreateRequest(senderId, 999L, "쿠폰의 내용", Category.COFFEE.getValue());

        assertThatThrownBy(() -> couponService.create(request));
    }

    @Test
    void 쿠폰의_상태를_변경할_수_있다() {
        Long senderId = memberRepository.save(createMember("oauth-루키-id", "루키@gmail.com", "루키")).getId();
        Long receiverId = memberRepository.save(createMember("oauth-키루-id", "키루@gmail.com", "키루")).getId();
        Long couponId = couponRepository.save(createCoupon(senderId, receiverId)).getId();

        couponService.updateCondition(couponId, receiverId,
            new CouponConditionUpdateRequest(Condition.FINISH.getValue()));

        assertThat(couponRepository.getById(couponId).getCondition()).isEqualTo(Condition.FINISH);
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
