package com.woowacourse.kkogkkog.reservation.application.dto.response;

import com.woowacourse.kkogkkog.coupon.domain.Category;
import com.woowacourse.kkogkkog.reservation.domain.Condition;
import com.woowacourse.kkogkkog.reservation.domain.repository.data.ReservationCouponMemberData;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ReservationResponse {

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

    public static ReservationResponse createResponse(final ReservationCouponMemberData response) {
        return new ReservationResponse(
            response.getReservationId(), response.getCouponId(),
            response.getSenderId(), response.getSenderName(), response.getSenderImageUrl(),
            response.getReceiverId(), response.getReceiverName(), response.getReceiverImageUrl(),
            response.getContent(), response.getCategory(), response.getMessage(), response.getCondition(),
            response.getAppointedTime(), response.getCreatedTime()
        );
    }
}
