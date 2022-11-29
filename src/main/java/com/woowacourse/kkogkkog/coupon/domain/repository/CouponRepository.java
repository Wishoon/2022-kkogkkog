package com.woowacourse.kkogkkog.coupon.domain.repository;

import com.woowacourse.kkogkkog.coupon.domain.Coupon;
import java.util.Optional;
import javax.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CouponRepository extends JpaRepository<Coupon, Long> {

    default Coupon getById(final Long couponId) {
        return findById(couponId)
            .orElseThrow(IllegalArgumentException::new);
    }

    default Coupon getWithOptimisticLockById(final Long couponId) {
        return findWithOptimisticLockById(couponId)
            .orElseThrow(IllegalArgumentException::new);
    }

    @Lock(LockModeType.OPTIMISTIC)
    @Query("select c from Coupon c where c.id = :couponId")
    Optional<Coupon> findWithOptimisticLockById(@Param("couponId") Long couponId);
}
