package com.woowacourse.kkogkkog.coupon.domain.repository;

import com.woowacourse.kkogkkog.coupon.domain.repository.data.CouponMemberData;
import java.util.List;

public interface CouponRepositoryCustom {

    List<CouponMemberData> findAllByMemberIdAndRequestType(final Long memberId, final String requestType);
}
