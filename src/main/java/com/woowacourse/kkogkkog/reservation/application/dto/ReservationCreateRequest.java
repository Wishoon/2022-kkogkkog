package com.woowacourse.kkogkkog.reservation.application.dto;

import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ReservationCreateRequest {

    private Long couponId;
    private String message;
    private LocalDateTime appointedTime;
}
