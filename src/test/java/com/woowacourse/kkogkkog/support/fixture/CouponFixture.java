package com.woowacourse.kkogkkog.support.fixture;

import com.woowacourse.kkogkkog.coupon.application.dto.request.CouponConditionUpdateRequest;
import com.woowacourse.kkogkkog.coupon.application.dto.request.CouponCreateRequest;
import com.woowacourse.kkogkkog.coupon.domain.Category;
import com.woowacourse.kkogkkog.coupon.domain.Condition;
import com.woowacourse.kkogkkog.coupon.domain.Coupon;
import java.util.List;
import java.util.UUID;

public enum CouponFixture {

    READY_쿠폰("준비 상태의 쿠폰 내용", Category.COFFEE, Condition.READY),
    IN_PROGRESS_쿠폰("진행 상태의 쿠폰 내용", Category.COFFEE, Condition.IN_PROGRESS),
    FINISH_쿠폰("종료 상태의 쿠폰 내용", Category.COFFEE, Condition.FINISH);

    private String content;
    private Category category;
    private Condition condition;

    CouponFixture(final String content, final Category category, final Condition condition) {
        this.content = content;
        this.category = category;
        this.condition = condition;
    }

    public static Coupon READY_쿠폰(Long senderId, Long receiverId) {
        return new Coupon(null, UUID.randomUUID(), senderId, receiverId,
            READY_쿠폰.content, READY_쿠폰.category, READY_쿠폰.condition);
    }

    public static Coupon IN_PROGRESS_쿠폰(Long senderId, Long receiverId) {
        return new Coupon(null, UUID.randomUUID(), senderId, receiverId,
            IN_PROGRESS_쿠폰.content, IN_PROGRESS_쿠폰.category, IN_PROGRESS_쿠폰.condition);
    }

    public static Coupon FINISH_쿠폰(Long senderId, Long receiverId) {
        return new Coupon(null, UUID.randomUUID(), senderId, receiverId,
            FINISH_쿠폰.content, FINISH_쿠폰.category, FINISH_쿠폰.condition);
    }

    // 쿠폰 생성 요청
    public static CouponCreateRequest 쿠폰을_생성하는_요청(List<Long> receiverId) {
        return new CouponCreateRequest(receiverId, "쿠폰의 내용", Category.COFFEE.getValue());
    }

    // 쿠폰 상태 변경 요청
    public static CouponConditionUpdateRequest 쿠폰의_상태를_변경하는_요청(String condition) {
        return new CouponConditionUpdateRequest(condition);
    }
}
