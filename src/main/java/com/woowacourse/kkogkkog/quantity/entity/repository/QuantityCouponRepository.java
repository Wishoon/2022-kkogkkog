package com.woowacourse.kkogkkog.quantity.entity.repository;

import com.woowacourse.kkogkkog.quantity.entity.QuantityCoupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuantityCouponRepository extends JpaRepository<QuantityCoupon, Long> {

    default QuantityCoupon getById(final Long quantityCouponId) {
        return findById(quantityCouponId)
            .orElseThrow(IllegalArgumentException::new);
    }
}
