package com.woowacourse.kkogkkog.coupon.application.dto.request;

import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class CouponCreateRequest {

    private List<Long> receiverIds;
    private String content;
    private String category;
}
