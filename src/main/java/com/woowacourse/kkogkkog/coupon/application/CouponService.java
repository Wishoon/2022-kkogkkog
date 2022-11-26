package com.woowacourse.kkogkkog.coupon.application;

import com.woowacourse.kkogkkog.coupon.application.dto.request.CouponConditionUpdateRequest;
import com.woowacourse.kkogkkog.coupon.application.dto.request.CouponCreateRequest;
import com.woowacourse.kkogkkog.coupon.domain.Category;
import com.woowacourse.kkogkkog.coupon.domain.Condition;
import com.woowacourse.kkogkkog.coupon.domain.Coupon;
import com.woowacourse.kkogkkog.coupon.domain.repository.CouponRepository;
import com.woowacourse.kkogkkog.member.domain.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class CouponService {

    private final CouponRepository couponRepository;
    private final MemberRepository memberRepository;

    public Long create(final CouponCreateRequest request) {
        validateExistsBySenderAndReceiver(request.getSenderId(), request.getReceiverId());

        Coupon coupon = couponRepository.save(createCoupon(request));
        return coupon.getId();
    }

    private void validateExistsBySenderAndReceiver(final Long senderId, final Long receiverId) {
        if (!memberRepository.existsById(senderId) || !memberRepository.existsById(receiverId)) {
            throw new IllegalArgumentException();
        }
    }

    private static Coupon createCoupon(final CouponCreateRequest request) {
        return Coupon.builder()
            .senderId(request.getSenderId())
            .receiverId(request.getReceiverId())
            .content(request.getContent())
            .category(Category.findCategory(request.getCategory()))
            .condition(Condition.READY)
            .build();
    }

    public void updateCondition(final Long couponId,
                                final Long invokeMemberId,
                                final CouponConditionUpdateRequest request) {
        validateExistsMember(invokeMemberId);
        Coupon coupon = couponRepository.getById(couponId);

        coupon.updateCondition(request.getCondition(), invokeMemberId);
    }

    private void validateExistsMember(final Long invokeMemberId) {
        if (!memberRepository.existsById(invokeMemberId)) {
            throw new IllegalArgumentException();
        }
    }
}
