package com.woowacourse.kkogkkog.reservation.domain;

import com.woowacourse.kkogkkog.common.base.BaseEntity;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Reservation extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @Column(name = "coupon_id", nullable = false)
    private Long couponId;

    @Column(name = "message")
    private String message;

    @Column(name = "appointed_time", nullable = false)
    private LocalDateTime appointedTime;

    @Embedded
    private Condition condition;

    @Builder
    public Reservation(final Long id,
                       final Long memberId,
                       final Long couponId,
                       final String message,
                       final LocalDateTime appointedTime,
                       final Condition condition) {
        this.id = id;
        this.memberId = memberId;
        this.couponId = couponId;
        this.message = message;
        this.appointedTime = appointedTime;
        this.condition = condition;
    }
}
