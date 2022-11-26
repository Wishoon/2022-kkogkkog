package com.woowacourse.kkogkkog.coupon.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class CouponTest {

    @Test
    void 쿠폰_생성시_SENDER와_RECEIVER가_동일할_경우_예외가_발생한다() {
        assertThatThrownBy(() -> new Coupon(null, 1L, 1L, "쿠폰의 내용", Category.COFFEE, Condition.READY))
            .isInstanceOf(IllegalArgumentException.class);
    }
}
