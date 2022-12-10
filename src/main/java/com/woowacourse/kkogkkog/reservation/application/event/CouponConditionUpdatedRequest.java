package com.woowacourse.kkogkkog.reservation.application.event;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CouponConditionUpdatedRequest {

    private Long couponId;
    private Long memberId;
    private String condition;
}
