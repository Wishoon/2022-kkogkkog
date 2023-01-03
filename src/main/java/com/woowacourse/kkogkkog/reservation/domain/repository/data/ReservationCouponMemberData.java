package com.woowacourse.kkogkkog.reservation.domain.repository.data;

import com.woowacourse.kkogkkog.coupon.domain.Category;
import com.woowacourse.kkogkkog.reservation.domain.Condition;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
public class ReservationCouponMemberData {

    private Long reservationId;
    private Long couponId;
    private Long senderId;
    private String senderName;
    private String senderImageUrl;
    private Long receiverId;
    private String receiverName;
    private String receiverImageUrl;
    private String content;
    private Category category;
    private String message;
    private Condition condition;
    private LocalDateTime appointedTime;
    private LocalDateTime createdTime;
}
