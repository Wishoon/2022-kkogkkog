package com.woowacourse.kkogkkog.quantity;

import com.woowacourse.kkogkkog.auth.presentation.AuthMember;
import com.woowacourse.kkogkkog.quantity.application.QuantityCouponService;
import com.woowacourse.kkogkkog.quantity.application.dto.QuantityCouponCreateRequest;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/quantity-coupons")
@RestController
public class QuantityCouponController {

    private static final String COUPON_KEY_PREFIX = "QUANTITY_COUPON_";

    private final QuantityCouponService quantityCouponService;

    @PostMapping
    public ResponseEntity<Void> create(@AuthMember Long memberId,
                                       @RequestBody QuantityCouponCreateRequest request) {
        Long quantityCouponId = quantityCouponService.create(memberId, request);

        return ResponseEntity.created(URI.create("/api/quantity-coupon/" + quantityCouponId)).build();
    }

    @PutMapping("/{quantityCouponId}/stock")
    public ResponseEntity<Void> decreaseStock(@PathVariable Long quantityCouponId) {
        quantityCouponService.decreaseStock(COUPON_KEY_PREFIX + quantityCouponId, quantityCouponId);

        return ResponseEntity.noContent().build();
    }
}
