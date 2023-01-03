package com.woowacourse.kkogkkog.reservation.application;

import static com.woowacourse.kkogkkog.support.fixture.CouponFixture.READY_쿠폰;
import static com.woowacourse.kkogkkog.support.fixture.MemberFixture.발신자_회원;
import static com.woowacourse.kkogkkog.support.fixture.MemberFixture.수신자_회원;
import static com.woowacourse.kkogkkog.support.fixture.ReservationFixture.예약을_생성하는_요청;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.kkogkkog.annotation.IntegrationTest;
import com.woowacourse.kkogkkog.coupon.domain.repository.CouponRepository;
import com.woowacourse.kkogkkog.member.domain.MemberRepository;
import com.woowacourse.kkogkkog.reservation.application.dto.request.ReservationCreateRequest;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@IntegrationTest
class ReservationServiceTest {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private CouponRepository couponRepository;
    @Autowired
    private ReservationService reservationService;

    @Test
    void 예약을_생성할_수_있다() {
        Long senderId = memberRepository.save(발신자_회원()).getId();
        Long receiverId = memberRepository.save(수신자_회원()).getId();
        Long couponId = couponRepository.save(READY_쿠폰(senderId, receiverId)).getId();

        ReservationCreateRequest request = 예약을_생성하는_요청(couponId, LocalDateTime.of(2022, 12, 01, 00, 00, 00));
        Long actual = reservationService.save(receiverId, request);

        assertThat(actual).isEqualTo(1L);
    }

    @Test
    void 예약을_현재_시간보다_과거의_시간으로_요청할_경우_예외가_발생한다() {
        Long senderId = memberRepository.save(발신자_회원()).getId();
        Long receiverId = memberRepository.save(수신자_회원()).getId();
        Long couponId = couponRepository.save(READY_쿠폰(senderId, receiverId)).getId();

        ReservationCreateRequest request = 예약을_생성하는_요청(couponId, LocalDateTime.of(2021, 12, 01, 00, 00, 00));
        assertThatThrownBy(() -> reservationService.save(receiverId, request))
            .isInstanceOf(IllegalArgumentException.class);
    }
}
