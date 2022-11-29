package com.woowacourse.kkogkkog.quantity.entity;

import static com.woowacourse.kkogkkog.support.fixture.QuantityCouponFixture.수량_쿠폰;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class QuantityCouponTest {

    @Test
    void 수량_쿠폰을_생성할_때_재고를_음수로_가질경우_예외가_발생한다() {
        assertThatThrownBy(() -> 수량_쿠폰(1L, -1))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 수량_쿠폰의_재고를_감소시킬_수_있다() {
        QuantityCoupon quantityCoupon = 수량_쿠폰(1L, 100);

        quantityCoupon.decreaseStock();

        assertThat(quantityCoupon.getStock()).isEqualTo(99);
    }

    @Test
    void 수량_쿠폰의_재고가_0개일_때_재고를_감소시킬_경우_예외가_발생한다() {
        QuantityCoupon quantityCoupon = 수량_쿠폰(1L, 0);

        assertThatThrownBy(() -> quantityCoupon.decreaseStock())
            .isInstanceOf(IllegalArgumentException.class);
    }
}
