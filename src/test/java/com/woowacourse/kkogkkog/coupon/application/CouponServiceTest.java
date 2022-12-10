package com.woowacourse.kkogkkog.coupon.application;

import static com.woowacourse.kkogkkog.support.fixture.CouponFixture.IN_PROGRESS_쿠폰;
import static com.woowacourse.kkogkkog.support.fixture.CouponFixture.READY_쿠폰;
import static com.woowacourse.kkogkkog.support.fixture.CouponFixture.쿠폰을_생성하는_요청;
import static com.woowacourse.kkogkkog.support.fixture.CouponFixture.쿠폰의_상태를_변경하는_요청;
import static com.woowacourse.kkogkkog.support.fixture.MemberFixture.발신자_회원;
import static com.woowacourse.kkogkkog.support.fixture.MemberFixture.수신자_회원;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.woowacourse.kkogkkog.annotation.IntegrationTest;
import com.woowacourse.kkogkkog.coupon.application.dto.request.CouponConditionUpdateRequest;
import com.woowacourse.kkogkkog.coupon.application.dto.request.CouponCreateRequest;
import com.woowacourse.kkogkkog.coupon.domain.Condition;
import com.woowacourse.kkogkkog.coupon.domain.repository.CouponRepository;
import com.woowacourse.kkogkkog.member.domain.MemberRepository;
import com.woowacourse.kkogkkog.reservation.application.event.CouponConditionUpdatedRequest;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;

@IntegrationTest
class CouponServiceTest {

    @Autowired
    private CouponService couponService;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private CouponRepository couponRepository;

    @Test
    void 쿠폰을_생성할_수_있다() {
        Long senderId = memberRepository.save(발신자_회원()).getId();
        Long receiverId = memberRepository.save(수신자_회원()).getId();

        CouponCreateRequest request = 쿠폰을_생성하는_요청(List.of(receiverId));

        couponService.create(1L, request);
        assertThat(couponRepository.findAll()).hasSize(1);
    }

    @Test
    void 쿠폰을_생성에서_존재하지_않는_회원이_포함된_경우_예외가_발생한다() {
        memberRepository.save(발신자_회원()).getId();

        CouponCreateRequest request = 쿠폰을_생성하는_요청(List.of(999L));

        assertThatThrownBy(() -> couponService.create(1L, request));
    }

    @Test
    void 쿠폰의_상태를_변경할_수_있다() {
        Long senderId = memberRepository.save(발신자_회원()).getId();
        Long receiverId = memberRepository.save(수신자_회원()).getId();
        Long couponId = couponRepository.save(READY_쿠폰(senderId, receiverId)).getId();

        couponService.updateCondition(couponId, receiverId, 쿠폰의_상태를_변경하는_요청(Condition.FINISH.getValue()));

        assertThat(couponRepository.getById(couponId).getCondition()).isEqualTo(Condition.FINISH);
    }

    @Test
    void 쿠폰의_상태를_이벤트_요청을_통해_변경할_수_있다() {
        Long senderId = memberRepository.save(발신자_회원()).getId();
        Long receiverId = memberRepository.save(수신자_회원()).getId();
        Long couponId = couponRepository.save(READY_쿠폰(senderId, receiverId)).getId();

        CouponConditionUpdatedRequest actual = new CouponConditionUpdatedRequest(couponId, receiverId,
            Condition.IN_PROGRESS.getValue());
        couponService.updateCondition(actual);

        assertThat(couponRepository.getById(couponId).getCondition()).isEqualTo(Condition.IN_PROGRESS);
    }

    @Test
    void 쿠폰의_상태를_변경할_때_먼저_요청된_스레드의_값만_반영된다() throws InterruptedException {
        Long senderId = memberRepository.save(발신자_회원()).getId();
        Long receiverId = memberRepository.save(수신자_회원()).getId();
        Long couponId = couponRepository.save(IN_PROGRESS_쿠폰(senderId, receiverId)).getId();

        ExecutorService executor = Executors.newFixedThreadPool(32);
        Future<?> future1 = executor.submit(() -> couponService.updateCondition(
            couponId, receiverId, new CouponConditionUpdateRequest(Condition.READY.getValue())));
        Future<?> future2 = executor.submit(() -> couponService.updateCondition(
            couponId, receiverId, new CouponConditionUpdateRequest(Condition.FINISH.getValue())));

        Exception result = new Exception();
        try {
            future1.get();
            future2.get();
        } catch (final ExecutionException e) {
            result = (Exception) e.getCause();
        }
        assertTrue(result instanceof OptimisticLockingFailureException);
    }
}
