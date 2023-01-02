package com.woowacourse.kkogkkog.reservation.application;

import com.woowacourse.kkogkkog.reservation.application.dto.request.ReservationCreateRequest;
import com.woowacourse.kkogkkog.reservation.application.event.CouponConditionUpdatedRequest;
import com.woowacourse.kkogkkog.reservation.domain.Condition;
import com.woowacourse.kkogkkog.reservation.domain.Reservation;
import com.woowacourse.kkogkkog.reservation.domain.repository.ReservationRepository;
import com.woowacourse.kkogkkog.reservation.domain.validator.ReservationValidator;
import java.time.Clock;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationValidator reservationValidator;
    private final Clock clock;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public Long save(final Long memberId, final ReservationCreateRequest request) {
        reservationValidator.validateExistsCoupon(request.getCouponId());
        validateReservationAppointedTime(request.getAppointedTime());
        Reservation reservation = createReservation(memberId, request);

        eventPublisher.publishEvent(new CouponConditionUpdatedRequest(request.getCouponId(), memberId, "in_progress"));

        return reservationRepository.save(reservation).getId();
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
            .condition(Condition.REQUESTED)
            .build();
    }
}
