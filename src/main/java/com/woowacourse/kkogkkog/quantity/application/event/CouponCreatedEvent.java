package com.woowacourse.kkogkkog.quantity.application.event;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@ToString
@AllArgsConstructor
@Getter
public class CouponCreatedEvent {

    private UUID serialNumber;
    private Long senderId;
    private Long receiverId;
    private String content;
}
