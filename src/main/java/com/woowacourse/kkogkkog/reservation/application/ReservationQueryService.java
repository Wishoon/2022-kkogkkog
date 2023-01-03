package com.woowacourse.kkogkkog.reservation.application;

import com.woowacourse.kkogkkog.common.exception.ErrorType;
import com.woowacourse.kkogkkog.reservation.application.dto.response.ReservationResponse;
import com.woowacourse.kkogkkog.reservation.domain.repository.ReservationRepository;
import com.woowacourse.kkogkkog.reservation.domain.repository.data.ReservationCouponMemberData;
import com.woowacourse.kkogkkog.reservation.exeception.ReservationNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ReservationQueryService {

    private static final String SENDER = "sender";
    private static final String RECEIVER = "receiver";

    private final ReservationRepository reservationRepository;

    public ReservationResponse find(final Long reservationId) {
        ReservationCouponMemberData response = reservationRepository.findFetchById(reservationId)
            .orElseThrow(() -> new ReservationNotFoundException(ErrorType.RESERVATION_NOT_FOUND));

        return ReservationResponse.createResponse(response);
    }
}
