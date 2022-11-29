package com.woowacourse.kkogkkog.coupon.application;

import static com.woowacourse.kkogkkog.support.fixture.CouponFixture.READY_쿠폰;
import static com.woowacourse.kkogkkog.support.fixture.CouponFixture.쿠폰을_생성하는_요청;
import static com.woowacourse.kkogkkog.support.fixture.CouponFixture.쿠폰의_상태를_변경하는_요청;
import static com.woowacourse.kkogkkog.support.fixture.MemberFixture.발신자_회원;
import static com.woowacourse.kkogkkog.support.fixture.MemberFixture.수신자_회원;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.kkogkkog.annotation.IntegrationTest;
import com.woowacourse.kkogkkog.coupon.application.dto.request.CouponCreateRequest;
import com.woowacourse.kkogkkog.coupon.domain.Condition;
import com.woowacourse.kkogkkog.coupon.domain.repository.CouponRepository;
import com.woowacourse.kkogkkog.member.domain.MemberRepository;
import java.util.List;
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
        Long senderId = memberRepository.save(발신자_회원()).getId();
        Long receiverId = memberRepository.save(수신자_회원()).getId();

        CouponCreateRequest request = 쿠폰을_생성하는_요청(List.of(receiverId));

        couponService.create(1L, request);
        assertThat(couponRepository.findAll()).hasSize(1);
    }

    @Test
    void 쿠폰을_생성에서_존재하지_않는_회원이_포함된_경우_예외가_발생한다() {
        memberRepository.save(발신자_회원()).getId();

        CouponCreateRequest request = 쿠폰을_생성하는_요청(List.of(999L));

        assertThatThrownBy(() -> couponService.create(1L, request));
    }

    @Test
    void 쿠폰의_상태를_변경할_수_있다() {
        Long senderId = memberRepository.save(발신자_회원()).getId();
        Long receiverId = memberRepository.save(수신자_회원()).getId();
        Long couponId = couponRepository.save(READY_쿠폰(senderId, receiverId)).getId();

        couponService.updateCondition(couponId, receiverId, 쿠폰의_상태를_변경하는_요청(Condition.FINISH.getValue()));

        assertThat(couponRepository.getById(couponId).getCondition()).isEqualTo(Condition.FINISH);
    }
}
