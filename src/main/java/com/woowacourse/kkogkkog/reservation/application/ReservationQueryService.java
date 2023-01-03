package com.woowacourse.kkogkkog.reservation.application;

import com.woowacourse.kkogkkog.common.exception.ErrorType;
import com.woowacourse.kkogkkog.reservation.application.dto.response.ReservationResponse;
import com.woowacourse.kkogkkog.reservation.application.dto.response.ReservationsResponse;
import com.woowacourse.kkogkkog.reservation.domain.repository.ReservationRepository;
import com.woowacourse.kkogkkog.reservation.domain.repository.data.ReservationCouponMemberData;
import com.woowacourse.kkogkkog.reservation.exeception.ReservationNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
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

    public ReservationsResponse findAllByMember(final Long memberId) {
        List<ReservationCouponMemberData> responses = new ArrayList<>();
        for (String requestType : List.of(SENDER, RECEIVER)) {
            responses.addAll(reservationRepository.findAllByMemberIdAndRequestType(memberId, requestType));
        }

        return new ReservationsResponse(responses.stream()
            .sorted(Comparator.comparing(ReservationCouponMemberData::getAppointedTime))
            .map(ReservationResponse::createResponse)
            .collect(Collectors.toList()));
    }
}
