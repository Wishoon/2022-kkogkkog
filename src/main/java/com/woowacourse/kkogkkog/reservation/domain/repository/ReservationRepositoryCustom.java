package com.woowacourse.kkogkkog.reservation.domain.repository;

import com.woowacourse.kkogkkog.reservation.domain.repository.data.ReservationCouponMemberData;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ReservationRepositoryCustom {

    Optional<ReservationCouponMemberData> findFetchById(final Long reservationId);

    List<ReservationCouponMemberData> findAllByMemberIdAndRequestType(final Long memberId, final String requestType);

    List<ReservationCouponMemberData> findAllByMemberIdAndRequestTypeAndNowDateTime(final Long memberId,
                                                                                    final String requestType,
                                                                                    final LocalDateTime nowDateTime);
}
