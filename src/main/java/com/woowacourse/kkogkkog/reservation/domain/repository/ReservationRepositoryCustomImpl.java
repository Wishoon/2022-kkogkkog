package com.woowacourse.kkogkkog.reservation.domain.repository;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.QBean;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.woowacourse.kkogkkog.coupon.domain.QCoupon;
import com.woowacourse.kkogkkog.member.domain.QMember;
import com.woowacourse.kkogkkog.reservation.domain.Condition;
import com.woowacourse.kkogkkog.reservation.domain.QReservation;
import com.woowacourse.kkogkkog.reservation.domain.repository.data.ReservationCouponMemberData;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ReservationRepositoryCustomImpl implements ReservationRepositoryCustom {

    private final QReservation reservation = new QReservation("reservation");
    private final QCoupon coupon = new QCoupon("coupon");
    private final QMember sender = new QMember("sender");
    private final QMember receiver = new QMember("receiver");

    private final JPAQueryFactory queryFactory;

    @Override
    public List<ReservationCouponMemberData> findAllByMemberIdAndRequestType(final Long memberId,
                                                                             final String requestType) {
        return queryFactory
            .select(createReservationCouponMemberData())
            .from(reservation)
            .join(coupon).on(reservation.couponId.eq(coupon.id))
            .join(sender).on(coupon.senderId.eq(sender.id))
            .join(receiver).on(coupon.receiverId.eq(receiver.id))
            .where(memberIdEq(memberId, requestType))
            .fetch();
    }

    @Override
    public List<ReservationCouponMemberData> findAllByMemberIdAndRequestTypeAndNowDateTime(final Long memberId,
                                                                                           final String requestType,
                                                                                           final LocalDateTime nowDateTime) {
        return queryFactory
            .select(createReservationCouponMemberData())
            .from(reservation)
            .join(coupon).on(reservation.couponId.eq(coupon.id))
            .join(sender).on(coupon.senderId.eq(sender.id))
            .join(receiver).on(coupon.receiverId.eq(receiver.id))
            .where(memberIdEq(memberId, requestType))
            .where(reservation.condition.eq(Condition.APPROVED))
            .where(reservation.appointedTime.goe(nowDateTime))
            .fetch();
    }

    private Predicate memberIdEq(final Long memberId, final String requestType) {
        if (requestType.equals("sender")) {
            return coupon.senderId.eq(memberId);
        }
        if (requestType.equals("receiver")) {
            return coupon.receiverId.eq(memberId);
        }

        throw new IllegalArgumentException();
    }

    private QBean<ReservationCouponMemberData> createReservationCouponMemberData() {
        return Projections.fields(ReservationCouponMemberData.class,
            reservation.id.as("reservationId"),
            coupon.id.as("couponId"),
            sender.id.as("senderId"),
            sender.username.as("senderName"),
            sender.imageUrl.as("senderImageUrl"),
            receiver.id.as("receiverId"),
            receiver.username.as("receiverName"),
            receiver.imageUrl.as("receiverImageUrl"),
            coupon.content,
            coupon.category,
            reservation.message,
            reservation.condition,
            reservation.appointedTime,
            coupon.createdTime
        );
    }
}
