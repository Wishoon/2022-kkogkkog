package com.woowacourse.kkogkkog.support.fixture;

import com.woowacourse.kkogkkog.reservation.application.dto.request.ReservationCreateRequest;
import com.woowacourse.kkogkkog.reservation.domain.Condition;
import com.woowacourse.kkogkkog.reservation.domain.Reservation;
import java.time.LocalDateTime;

public enum ReservationFixture {

    REQUESTED_상태의_예약("예약을 할 때 작성하는 메시지", Condition.REQUESTED),
    APPROVED_상태의_예약("예약을 할 때 작성하는 메시지", Condition.APPROVED),
    REQUESTED_예약("예약을 할 때 작성하는 메시지", Condition.REQUESTED),
    ;

    private String message;
    private Condition condition;

    ReservationFixture(final String message, final Condition condition) {
        this.message = message;
        this.condition = condition;
    }

    public static Reservation REQUESTED_상태의_예약(final Long memberId, final Long couponId,
                                               final LocalDateTime appointedTime) {
        return new Reservation(null, memberId, couponId,
            REQUESTED_상태의_예약.message, appointedTime, REQUESTED_상태의_예약.condition);
    }

    public static Reservation APPROVED_상태의_예약(final Long memberId, final Long couponId,
                                              final LocalDateTime appointedTime) {
        return new Reservation(null, memberId, couponId,
            APPROVED_상태의_예약.message, appointedTime, APPROVED_상태의_예약.condition);
    }

    public static ReservationCreateRequest 예약을_생성하는_요청(final Long couponId, final LocalDateTime appointedTime) {
        return new ReservationCreateRequest(couponId, REQUESTED_예약.message, appointedTime);
    }
}
