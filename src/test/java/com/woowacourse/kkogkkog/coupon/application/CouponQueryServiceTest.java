package com.woowacourse.kkogkkog.coupon.application;

import static com.woowacourse.kkogkkog.support.fixture.CouponFixture.READY_쿠폰;
import static com.woowacourse.kkogkkog.support.fixture.MemberFixture.발신자_회원;
import static com.woowacourse.kkogkkog.support.fixture.MemberFixture.수신자_회원;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.kkogkkog.annotation.IntegrationTest;
import com.woowacourse.kkogkkog.coupon.application.dto.response.CouponResponse;
import com.woowacourse.kkogkkog.coupon.domain.repository.CouponRepository;
import com.woowacourse.kkogkkog.member.domain.MemberRepository;
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
        Long senderId = memberRepository.save(발신자_회원()).getId();
        Long receiverId = memberRepository.save(수신자_회원()).getId();
        Long couponId = couponRepository.save(READY_쿠폰(senderId, receiverId)).getId();

        CouponResponse actual = couponQueryService.find(couponId);

        assertThat(actual.getCouponId()).isEqualTo(1L);
    }

    // CustomException 기능 제작 전까지 InvalidDataAccessApiUsageException 을 사용
    @Test
    void 쿠폰을_조회할_때_존재하지_않는_쿠폰_ID로_조회하는_경우_예외가_발생한다() {
        memberRepository.save(발신자_회원()).getId();
        memberRepository.save(수신자_회원()).getId();

        assertThatThrownBy(() -> couponQueryService.find(999L))
            .isInstanceOf(InvalidDataAccessApiUsageException.class);
    }
}
