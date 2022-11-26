package com.woowacourse.kkogkkog.coupon.application;

import com.woowacourse.kkogkkog.coupon.application.dto.response.CouponResponse;
import com.woowacourse.kkogkkog.coupon.domain.Coupon;
import com.woowacourse.kkogkkog.coupon.domain.repository.CouponRepository;
import com.woowacourse.kkogkkog.member.domain.Member;
import com.woowacourse.kkogkkog.member.domain.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class CouponQueryService {

    private final CouponRepository couponRepository;
    private final MemberRepository memberRepository;

    public CouponResponse find(final Long couponId) {
        Coupon coupon = couponRepository.getById(couponId);
        Member sender = memberRepository.getById(coupon.getSenderId());
        Member receiver = memberRepository.getById(coupon.getReceiverId());

        return CouponResponse.createResponse(coupon, sender, receiver);
    }
}
