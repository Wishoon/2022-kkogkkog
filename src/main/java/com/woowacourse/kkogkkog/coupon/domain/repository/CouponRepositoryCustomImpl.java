package com.woowacourse.kkogkkog.coupon.domain.repository;

import static com.woowacourse.kkogkkog.coupon.domain.QCoupon.coupon;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.QBean;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.woowacourse.kkogkkog.coupon.domain.repository.data.CouponMemberData;
import com.woowacourse.kkogkkog.member.domain.QMember;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CouponRepositoryCustomImpl implements CouponRepositoryCustom {

    private final QMember sender = new QMember("sender");
    private final QMember receiver = new QMember("receiver");

    private final JPAQueryFactory queryFactory;

    @Override
    public List<CouponMemberData> findAllByMemberIdAndRequestType(final Long memberId, final String requestType) {
        return queryFactory
            .select(createCouponMemberResponse())
            .from(coupon)
            .join(sender).on(coupon.senderId.eq(sender.id))
            .join(receiver).on(coupon.receiverId.eq(receiver.id))
            .where(memberIdEq(memberId, requestType))
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

    private QBean<CouponMemberData> createCouponMemberResponse() {
        return Projections.fields(CouponMemberData.class,
            coupon.id.as("couponId"),
            sender.id.as("senderId"),
            sender.username.as("senderName"),
            sender.imageUrl.as("senderImageUrl"),
            receiver.id.as("receiverId"),
            receiver.username.as("receiverName"),
            receiver.imageUrl.as("receiverImageUrl"),
            coupon.content,
            coupon.category,
            coupon.condition,
            coupon.createdTime
        );
    }
}
