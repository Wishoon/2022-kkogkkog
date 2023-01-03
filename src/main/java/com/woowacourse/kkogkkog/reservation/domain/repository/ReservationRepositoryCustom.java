package com.woowacourse.kkogkkog.reservation.domain.repository;

import com.woowacourse.kkogkkog.reservation.domain.repository.data.ReservationCouponMemberData;
import java.time.LocalDateTime;
import java.util.List;

public interface ReservationRepositoryCustom {

    List<ReservationCouponMemberData> findAllByMemberIdAndRequestType(final Long memberId, final String requestType);

    List<ReservationCouponMemberData> findAllByMemberIdAndRequestTypeAndNowDateTime(final Long memberId,
                                                                                    final String requestType,
                                                                                    final LocalDateTime nowDateTime);
}
