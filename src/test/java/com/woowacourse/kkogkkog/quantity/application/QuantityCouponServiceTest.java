package com.woowacourse.kkogkkog.quantity.application;

import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.kkogkkog.annotation.IntegrationTest;
import com.woowacourse.kkogkkog.member.domain.Member;
import com.woowacourse.kkogkkog.member.domain.MemberRepository;
import com.woowacourse.kkogkkog.member.domain.ProviderType;
import com.woowacourse.kkogkkog.quantity.application.dto.QuantityCouponCreateRequest;
import com.woowacourse.kkogkkog.quantity.entity.repository.QuantityCouponRepository;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@IntegrationTest
class QuantityCouponServiceTest {

    private static final String COUPON_KEY_PREFIX = "QUANTITY_COUPON_";

    @Autowired
    private QuantityCouponService quantityCouponService;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private QuantityCouponRepository quantityCouponRepository;

    @Test
    void 수량_쿠폰을_생성할_수_있다() {
        Long memberId = memberRepository.save(createMember("oauth-루키-id", "루키@gmail.com", "루키")).getId();

        quantityCouponService.create(memberId, new QuantityCouponCreateRequest("수량 쿠폰에 담을 내용", "수량 쿠폰의 카테고리", 10));

        assertThat(1).isEqualTo(quantityCouponRepository.findAll().size());
    }

    @Test
    void 수량_쿠폰의_재고를_감소시킬_수_있다() {
        Long memberId = memberRepository.save(createMember("oauth-루키-id", "루키@gmail.com", "루키")).getId();
        Long quantityCouponId = quantityCouponService.create(memberId,
            new QuantityCouponCreateRequest("수량 쿠폰에 담을 내용", "수량 쿠폰의 카테고리", 10));

        quantityCouponService.decreaseStock(COUPON_KEY_PREFIX + quantityCouponId, quantityCouponId);
    }

    @Test
    void 수량_쿠폰의_재고를_100명이_동시에_요청하더라도_재고를_감소시킬_수_있다() throws InterruptedException {
        Long memberId = memberRepository.save(createMember("oauth-루키-id", "루키@gmail.com", "루키")).getId();
        Long quantityCouponId = quantityCouponService.create(memberId,
            new QuantityCouponCreateRequest("수량 쿠폰에 담을 내용", "수량 쿠폰의 카테고리", 100));

        int numberOfThreads = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(32);
        CountDownLatch latch = new CountDownLatch(numberOfThreads);

        for (int i = 0; i < numberOfThreads; i++) {
            executorService.submit(() -> {
                try {
                    quantityCouponService.decreaseStock("QUANTITY_COUPON_" + quantityCouponId, quantityCouponId);
                } finally {
                    latch.countDown();
                }
            });
        }
        latch.await();

        assertThat(quantityCouponRepository.getById(quantityCouponId).getStock()).isZero();
    }

    private static Member createMember(final String providerId, final String email, final String username) {
        return Member.builder()
            .providerId(providerId)
            .email(email)
            .username(username)
            .imageUrl("https://image.com")
            .providerType(ProviderType.GITHUB).build();
    }
}
