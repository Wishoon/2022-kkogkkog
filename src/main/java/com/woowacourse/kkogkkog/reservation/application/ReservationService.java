package com.woowacourse.kkogkkog.reservation.application;

import com.woowacourse.kkogkkog.coupon.domain.repository.CouponRepository;
import com.woowacourse.kkogkkog.reservation.application.dto.ReservationCreateRequest;
import com.woowacourse.kkogkkog.reservation.domain.Condition;
import com.woowacourse.kkogkkog.reservation.domain.Reservation;
import com.woowacourse.kkogkkog.reservation.domain.repository.ReservationRepository;
import com.woowacourse.kkogkkog.reservation.domain.strategy.ConditionTypeRepository;
import java.time.Clock;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ConditionTypeRepository conditionTypeRepository;
    private final CouponRepository couponRepository;
    private final Clock clock;

    @Transactional
    public Long save(final Long memberId, final ReservationCreateRequest request) {
        validateExistsCoupon(request.getCouponId());
        validateReservationAppointedTime(request.getAppointedTime());
        Reservation reservation = createReservation(memberId, request);

        return reservationRepository.save(reservation).getId();
    }

    private void validateExistsCoupon(final Long couponId) {
        if (!couponRepository.existsById(couponId)) {
            throw new IllegalArgumentException("존재하지 않는 쿠폰입니다.");
        }
    }

    private void validateReservationAppointedTime(final LocalDateTime appointedTime) {
        if (appointedTime.isBefore(LocalDateTime.now(clock))) {
            throw new IllegalArgumentException("현재 시간보다 이전에 예약을 할 수 없습니다.");
        }
    }

    private Reservation createReservation(final Long memberId, final ReservationCreateRequest request) {
        return Reservation.builder()
            .memberId(memberId)
            .couponId(request.getCouponId())
            .message(request.getMessage())
            .appointedTime(request.getAppointedTime())
            .condition(new Condition("REQUESTED", conditionTypeRepository))
            .build();
    }
}
