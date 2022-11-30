package com.woowacourse.kkogkkog.quantity.application;

import static com.woowacourse.kkogkkog.support.fixture.MemberFixture.발신자_회원;
import static com.woowacourse.kkogkkog.support.fixture.MemberFixture.수신자_회원;
import static com.woowacourse.kkogkkog.support.fixture.QuantityCouponFixture.수량_쿠폰을_생성하는_요청;
import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.kkogkkog.annotation.IntegrationTest;
import com.woowacourse.kkogkkog.coupon.domain.repository.CouponRepository;
import com.woowacourse.kkogkkog.member.domain.MemberRepository;
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
    private CouponRepository couponRepository;
    @Autowired
    private QuantityCouponRepository quantityCouponRepository;

    @Test
    void 수량_쿠폰을_생성할_수_있다() {
        Long memberId = memberRepository.save(발신자_회원()).getId();

        quantityCouponService.create(memberId, new QuantityCouponCreateRequest("수량 쿠폰에 담을 내용", "수량 쿠폰의 카테고리", 10));

        assertThat(1).isEqualTo(quantityCouponRepository.findAll().size());
    }

    @Test
    void 수량_쿠폰의_재고를_감소시킬_수_있다() {
        Long senderId = memberRepository.save(발신자_회원()).getId();
        Long receiverId = memberRepository.save(수신자_회원()).getId();
        Long quantityCouponId = quantityCouponService.create(senderId, 수량_쿠폰을_생성하는_요청(10));

        quantityCouponService.decreaseStock(COUPON_KEY_PREFIX + quantityCouponId, receiverId, quantityCouponId);

        assertThat(quantityCouponRepository.getById(quantityCouponId).getStock()).isEqualTo(9);
    }

    @Test
    void 수량_쿠폰의_재고를_100명이_동시에_요청하더라도_재고를_감소시킬_수_있다() throws InterruptedException {
        Long senderId = memberRepository.save(발신자_회원()).getId();
        Long receiverId = memberRepository.save(수신자_회원()).getId();
        Long quantityCouponId = quantityCouponService.create(senderId, 수량_쿠폰을_생성하는_요청(100));

        int numberOfThreads = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(32);
        CountDownLatch latch = new CountDownLatch(numberOfThreads);

        for (int i = 0; i < numberOfThreads; i++) {
            executorService.submit(() -> {
                try {
                    quantityCouponService.decreaseStock(
                        COUPON_KEY_PREFIX + quantityCouponId, receiverId, quantityCouponId);
                } finally {
                    latch.countDown();
                }
            });
        }
        latch.await();

        assertThat(quantityCouponRepository.getById(quantityCouponId).getStock()).isZero();
    }
}
