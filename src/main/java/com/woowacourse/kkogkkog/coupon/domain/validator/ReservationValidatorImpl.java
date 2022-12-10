package com.woowacourse.kkogkkog.coupon.domain.validator;

import com.woowacourse.kkogkkog.coupon.domain.repository.CouponRepository;
import com.woowacourse.kkogkkog.reservation.domain.validator.ReservationValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ReservationValidatorImpl implements ReservationValidator {

    private final CouponRepository couponRepository;

    @Override
    public void validateExistsCoupon(final Long couponId) {
        if (!couponRepository.existsById(couponId)) {
            throw new IllegalArgumentException("존재하지 않는 쿠폰입니다.");
        }
    }
}
