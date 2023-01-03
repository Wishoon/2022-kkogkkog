package com.woowacourse.kkogkkog.reservation.application.dto.response;

import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ReservationsResponse {

    private List<ReservationResponse> data;

    public ReservationsResponse(final List<ReservationResponse> data) {
        this.data = data;
    }
}
