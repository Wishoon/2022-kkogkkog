package com.woowacourse.kkogkkog.coupon.presentation;

import com.woowacourse.kkogkkog.coupon.application.CouponQueryService;
import com.woowacourse.kkogkkog.coupon.application.CouponService;
import com.woowacourse.kkogkkog.coupon.application.dto.request.CouponConditionUpdateRequest;
import com.woowacourse.kkogkkog.coupon.application.dto.request.CouponCreateRequest;
import com.woowacourse.kkogkkog.coupon.application.dto.response.CouponResponse;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
    public ResponseEntity<Void> create(@RequestBody final CouponCreateRequest request) {
        Long couponId = couponService.create(request);

        return ResponseEntity.created(URI.create("/coupon/" + couponId)).build();
    }

    @GetMapping("/{couponId}")
    public ResponseEntity<CouponResponse> select(@PathVariable Long couponId) {
        CouponResponse response = couponQueryService.find(couponId);

        return ResponseEntity.ok(response);
    }

    // @AuthMember 커스텀 어노테이션을 만들기 전까지 1L로 하드코딩
    @PutMapping("/{couponId}/condition")
    public ResponseEntity<Void> updateCondition(@PathVariable Long couponId,
                                                @RequestBody CouponConditionUpdateRequest request) {
        couponService.updateCondition(couponId, 1L, request);

        return ResponseEntity.noContent().build();
    }
}
