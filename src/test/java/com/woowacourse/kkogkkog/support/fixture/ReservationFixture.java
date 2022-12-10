package com.woowacourse.kkogkkog.support.fixture;

import com.woowacourse.kkogkkog.reservation.application.dto.ReservationCreateRequest;
import com.woowacourse.kkogkkog.reservation.domain.Condition;
import java.time.LocalDateTime;

public enum ReservationFixture {

    REQUESTED_예약("예약을 할 때 작성하는 메시지", Condition.REQUESTED),
    ;

    private String message;
    private Condition condition;

    ReservationFixture(final String message, final Condition condition) {
        this.message = message;
        this.condition = condition;
    }

    public static ReservationCreateRequest 예약을_생성하는_요청(final Long couponId, final LocalDateTime appointedTime) {
        return new ReservationCreateRequest(couponId, REQUESTED_예약.message, appointedTime);
    }
}
