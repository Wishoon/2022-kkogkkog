package com.woowacourse.kkogkkog.coupon.presentation;

import com.woowacourse.kkogkkog.auth.presentation.AuthMember;
import com.woowacourse.kkogkkog.coupon.application.CouponQueryService;
import com.woowacourse.kkogkkog.coupon.application.CouponService;
import com.woowacourse.kkogkkog.coupon.application.dto.request.CouponConditionUpdateRequest;
import com.woowacourse.kkogkkog.coupon.application.dto.request.CouponCreateRequest;
import com.woowacourse.kkogkkog.coupon.application.dto.response.CouponResponse;
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
    public ResponseEntity<Void> create(@AuthMember Long memberId,
                                       @RequestBody final CouponCreateRequest request) {
        couponService.create(memberId, request);

        return ResponseEntity.created(null).build();
    }

    @GetMapping("/{couponId}")
    public ResponseEntity<CouponResponse> select(@PathVariable Long couponId) {
        CouponResponse response = couponQueryService.find(couponId);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{couponId}/condition")
    public ResponseEntity<Void> updateCondition(@AuthMember Long memberId,
                                                @PathVariable Long couponId,
                                                @RequestBody CouponConditionUpdateRequest request) {
        couponService.updateCondition(couponId, memberId, request);

        return ResponseEntity.noContent().build();
    }
}
