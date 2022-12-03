package com.woowacourse.kkogkkog.coupon.application.dto.response;

import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CouponsResponse {

    private List<CouponResponse> data;

    private CouponsResponse(final List<CouponResponse> data) {
        this.data = data;
    }

    public static CouponsResponse createResponse(final List<CouponResponse> responses) {
        return new CouponsResponse(responses);
    }
}
