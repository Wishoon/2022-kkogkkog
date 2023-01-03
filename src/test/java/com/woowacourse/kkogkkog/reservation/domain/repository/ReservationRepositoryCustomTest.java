package com.woowacourse.kkogkkog.reservation.domain.repository;

import static com.woowacourse.kkogkkog.support.fixture.CouponFixture.IN_PROGRESS_쿠폰;
import static com.woowacourse.kkogkkog.support.fixture.MemberFixture.발신자_회원;
import static com.woowacourse.kkogkkog.support.fixture.MemberFixture.수신자_회원;
import static com.woowacourse.kkogkkog.support.fixture.ReservationFixture.APPROVED_상태의_예약;
import static com.woowacourse.kkogkkog.support.fixture.ReservationFixture.REQUESTED_상태의_예약;
import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.kkogkkog.annotation.RepositoryTest;
import com.woowacourse.kkogkkog.coupon.domain.repository.CouponRepository;
import com.woowacourse.kkogkkog.member.domain.MemberRepository;
import com.woowacourse.kkogkkog.reservation.domain.repository.data.ReservationCouponMemberData;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@RepositoryTest
class ReservationRepositoryCustomTest {

    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private CouponRepository couponRepository;

    @Test
    void 회원의_ID와_요청_타입을_통해_예약정보를_조회할_수_있다() {
        Long senderId = memberRepository.save(발신자_회원()).getId();
        Long receiverId = memberRepository.save(수신자_회원()).getId();
        Long couponId = couponRepository.save(IN_PROGRESS_쿠폰(senderId, receiverId)).getId();
        reservationRepository.save(REQUESTED_상태의_예약(senderId, couponId, LocalDateTime.now()));

        List<ReservationCouponMemberData> actual = reservationRepository.findAllByMemberIdAndRequestType(
            senderId, "sender");

        assertThat(actual).hasSize(1);
    }

    @Test
    void 회원의_ID_요청_타입_현재시간을_통해_예약정보를_조회할_수_있다() {
        Long senderId = memberRepository.save(발신자_회원()).getId();
        Long receiverId = memberRepository.save(수신자_회원()).getId();
        Long couponId = couponRepository.save(IN_PROGRESS_쿠폰(senderId, receiverId)).getId();
        reservationRepository.save(APPROVED_상태의_예약(senderId, couponId, LocalDateTime.now()));

        List<ReservationCouponMemberData> actual = reservationRepository.findAllByMemberIdAndRequestTypeAndNowDateTime(
            senderId, "sender", LocalDateTime.now().minusDays(1));

        assertThat(actual).hasSize(1);
    }
}
