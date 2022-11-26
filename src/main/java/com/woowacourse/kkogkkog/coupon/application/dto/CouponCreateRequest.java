package com.woowacourse.kkogkkog.coupon.application.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CouponCreateRequest {

    private Long senderId;
    private Long receiverId;
    private String content;
    private String category;

    public CouponCreateRequest(final Long senderId,
                               final Long receiverId,
                               final String content,
                               final String category) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.content = content;
        this.category = category;
    }
}
