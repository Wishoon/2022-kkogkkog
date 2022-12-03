package com.woowacourse.kkogkkog.coupon.domain.repository.data;

import com.woowacourse.kkogkkog.coupon.domain.Category;
import com.woowacourse.kkogkkog.coupon.domain.Condition;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
public class CouponMemberData {

    private Long couponId;
    private Long senderId;
    private String senderName;
    private String senderImageUrl;
    private Long receiverId;
    private String receiverName;
    private String receiverImageUrl;
    private String content;
    private Category category;
    private Condition condition;
    private LocalDateTime createdTime;
}
