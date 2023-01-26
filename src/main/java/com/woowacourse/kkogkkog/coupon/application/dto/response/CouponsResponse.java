package com.woowacourse.kkogkkog.coupon.application.dto.response;

import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class CouponsResponse {

    private List<CouponResponse> data;

    public static CouponsResponse createResponse(final List<CouponResponse> responses) {
        return new CouponsResponse(responses);
    }
}
