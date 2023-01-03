package com.woowacourse.kkogkkog.reservation.domain.repository;

import com.woowacourse.kkogkkog.reservation.domain.repository.data.ReservationCouponMemberData;
import java.util.List;

public interface ReservationRepositoryCustom {

    List<ReservationCouponMemberData> findAllByMemberIdAndRequestType(final Long memberId, final String requestType);
}
