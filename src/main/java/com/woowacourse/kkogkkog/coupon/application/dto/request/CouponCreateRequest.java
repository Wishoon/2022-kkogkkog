package com.woowacourse.kkogkkog.coupon.application.dto.request;

import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CouponCreateRequest {

    private List<Long> receiverIds;
    private String content;
    private String category;

    public CouponCreateRequest(final List<Long> receiverIds,
                               final String content,
                               final String category) {
        this.receiverIds = receiverIds;
        this.content = content;
        this.category = category;
    }
}
