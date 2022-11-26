package com.woowacourse.kkogkkog.coupon.presentation;

import com.woowacourse.kkogkkog.coupon.application.CouponQueryService;
import com.woowacourse.kkogkkog.coupon.application.CouponService;
import com.woowacourse.kkogkkog.coupon.application.dto.request.CouponCreateRequest;
import com.woowacourse.kkogkkog.coupon.application.dto.response.CouponResponse;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/coupons")
public class CouponController {

    private final CouponService couponService;
    private final CouponQueryService couponQueryService;

    @PostMapping
    public ResponseEntity<Void> createCoupon(@RequestBody final CouponCreateRequest request) {
        Long couponId = couponService.create(request);

        return ResponseEntity.created(URI.create("/coupon/" + couponId)).build();
    }

    @GetMapping("/{couponId}")
    public ResponseEntity<CouponResponse> selectCoupon(@PathVariable Long couponId) {
        CouponResponse response = couponQueryService.find(couponId);

        return ResponseEntity.ok(response);
    }
}
