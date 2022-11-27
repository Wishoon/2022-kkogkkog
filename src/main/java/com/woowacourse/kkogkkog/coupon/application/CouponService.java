package com.woowacourse.kkogkkog.coupon.application;

import com.woowacourse.kkogkkog.coupon.application.dto.request.CouponConditionUpdateRequest;
import com.woowacourse.kkogkkog.coupon.application.dto.request.CouponCreateRequest;
import com.woowacourse.kkogkkog.coupon.domain.Category;
import com.woowacourse.kkogkkog.coupon.domain.Condition;
import com.woowacourse.kkogkkog.coupon.domain.Coupon;
import com.woowacourse.kkogkkog.coupon.domain.repository.CouponRepository;
import com.woowacourse.kkogkkog.member.domain.MemberRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CouponService {

    private final CouponRepository couponRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void create(final Long senderId, final CouponCreateRequest request) {
        validateExistsBySenderAndReceiver(senderId, request.getReceiverIds());

        couponRepository.saveAll(createCoupons(request, senderId));
    }

    private void validateExistsBySenderAndReceiver(final Long senderId, final List<Long> receiverIds) {
        if (!memberRepository.existsById(senderId) || !memberRepository.existsByIdIn(receiverIds)) {
            throw new IllegalArgumentException();
        }
    }

    private static List<Coupon> createCoupons(final CouponCreateRequest request, final Long senderId) {
        return request.getReceiverIds().stream()
            .map(receiverId -> createCoupon(request, senderId, receiverId))
            .collect(Collectors.toList());
    }

    private static Coupon createCoupon(final CouponCreateRequest request, final Long senderId, final Long receiverId) {
        return Coupon.builder()
            .senderId(senderId)
            .receiverId(receiverId)
            .content(request.getContent())
            .category(Category.findCategory(request.getCategory()))
            .condition(Condition.READY)
            .build();
    }

    @Transactional
    public void updateCondition(final Long couponId,
                                final Long invokeMemberId,
                                final CouponConditionUpdateRequest request) {
        validateExistsMember(invokeMemberId);
        Coupon coupon = couponRepository.getWithOptimisticLockById(couponId);

        coupon.updateCondition(request.getCondition(), invokeMemberId);
    }

    private void validateExistsMember(final Long invokeMemberId) {
        if (!memberRepository.existsById(invokeMemberId)) {
            throw new IllegalArgumentException();
        }
    }
}
