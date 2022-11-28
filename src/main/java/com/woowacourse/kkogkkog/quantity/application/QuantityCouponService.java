package com.woowacourse.kkogkkog.quantity.application;

import com.woowacourse.kkogkkog.common.distribute.DistributeLock;
import com.woowacourse.kkogkkog.member.domain.MemberRepository;
import com.woowacourse.kkogkkog.quantity.application.dto.QuantityCouponCreateRequest;
import com.woowacourse.kkogkkog.quantity.entity.QuantityCoupon;
import com.woowacourse.kkogkkog.quantity.entity.repository.QuantityCouponRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class QuantityCouponService {

    private final QuantityCouponRepository quantityCouponRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Long create(final Long memberId, final QuantityCouponCreateRequest request) {
        validateExistsMember(memberId);

        QuantityCoupon quantityCoupon = createQuantityCoupon(memberId, request);

        return quantityCouponRepository.save(quantityCoupon).getId();
    }

    private void validateExistsMember(final Long memberId) {
        if (!memberRepository.existsById(memberId)) {
            throw new IllegalArgumentException();
        }
    }

    @DistributeLock(key = "#key")
    public void decreaseStock(final String key, final Long quantityCouponId) {
        QuantityCoupon quantityCoupon = quantityCouponRepository.getById(quantityCouponId);
        quantityCoupon.decreaseStock();
    }

    private static QuantityCoupon createQuantityCoupon(final Long memberId, final QuantityCouponCreateRequest request) {
        return QuantityCoupon.builder()
            .serialNumber(UUID.randomUUID())
            .memberId(memberId)
            .content(request.getContent())
            .category(request.getCategory())
            .stock(request.getStock())
            .build();
    }
}
