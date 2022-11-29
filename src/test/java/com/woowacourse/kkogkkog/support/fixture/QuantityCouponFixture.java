package com.woowacourse.kkogkkog.support.fixture;

import com.woowacourse.kkogkkog.quantity.application.dto.QuantityCouponCreateRequest;
import com.woowacourse.kkogkkog.quantity.entity.QuantityCoupon;
import java.util.UUID;

public enum QuantityCouponFixture {

    수량_쿠폰("수량 쿠폰에 작성된 내용", "00 이벤트 쿠폰");

    private String content;
    private String category;

    QuantityCouponFixture(final String content, final String category) {
        this.content = content;
        this.category = category;
    }

    public static QuantityCoupon 수량_쿠폰(final Long memberId, final int stock) {
        return new QuantityCoupon(null, UUID.randomUUID(), memberId, 수량_쿠폰.content, 수량_쿠폰.category, stock);
    }

    // 수량 쿠폰 생성 요청
    public static QuantityCouponCreateRequest 수량_쿠폰을_생성하는_요청(int stock) {
        return new QuantityCouponCreateRequest(수량_쿠폰.content, 수량_쿠폰.category, stock);
    }
}
