package com.woowacourse.kkogkkog.reservation.application;

import static com.woowacourse.kkogkkog.support.fixture.CouponFixture.READY_쿠폰;
import static com.woowacourse.kkogkkog.support.fixture.MemberFixture.발신자_회원;
import static com.woowacourse.kkogkkog.support.fixture.MemberFixture.수신자_회원;
import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.kkogkkog.annotation.IntegrationTest;
import com.woowacourse.kkogkkog.coupon.domain.repository.CouponRepository;
import com.woowacourse.kkogkkog.member.domain.MemberRepository;
import com.woowacourse.kkogkkog.reservation.application.dto.request.ReservationCreateRequest;
import com.woowacourse.kkogkkog.reservation.application.dto.response.ReservationResponse;
import com.woowacourse.kkogkkog.reservation.application.dto.response.ReservationsResponse;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@IntegrationTest
class ReservationQueryServiceTest {

    @Autowired
    private ReservationQueryService reservationQueryService;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private CouponRepository couponRepository;
    @Autowired
    private ReservationService reservationService;

    @Test
    void 예약_ID를_통해서_예약_정보를_조회할_수_있다() {
        Long senderId = memberRepository.save(발신자_회원()).getId();
        Long receiverId = memberRepository.save(수신자_회원()).getId();
        Long couponId = couponRepository.save(READY_쿠폰(senderId, receiverId)).getId();
        Long reservationId = reservationService.save(
            receiverId, new ReservationCreateRequest(couponId, "예약 메시지", LocalDateTime.now().plusDays(1)));

        ReservationResponse actual = reservationQueryService.find(reservationId);

        assertThat(actual.getReservationId()).isEqualTo(reservationId);
    }

    @Test
    void 회원_ID를_통해서_예약_정보들을_조회할_수_있다() {
        Long senderId = memberRepository.save(발신자_회원()).getId();
        Long receiverId = memberRepository.save(수신자_회원()).getId();
        Long couponId = couponRepository.save(READY_쿠폰(senderId, receiverId)).getId();
        reservationService.save(
            receiverId, new ReservationCreateRequest(couponId, "예약 메시지", LocalDateTime.now().plusDays(1)));

        ReservationsResponse actual = reservationQueryService.findAllByMember(senderId);

        assertThat(actual.getData()).hasSize(1);
    }
}
