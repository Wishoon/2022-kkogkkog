package com.woowacourse.kkogkkog.acceptance;

import static com.woowacourse.kkogkkog.acceptance.fixture.MemberStepDefinition.로그인을_한다;
import static com.woowacourse.kkogkkog.acceptance.fixture.QuantityCouponStepDefinition.수량_쿠폰을_발급한다;
import static com.woowacourse.kkogkkog.acceptance.fixture.QuantityCouponStepDefinition.수량_쿠폰의_재고를_감소한다;
import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.kkogkkog.annotation.AcceptanceTest;
import org.junit.jupiter.api.Test;

@AcceptanceTest
public class QuantityCouponAcceptanceTest {

    @Test
    void 회원은_수량_쿠폰을_발급_할_수_있다() {
        String senderToken = 로그인을_한다("github", "ROOKIE_OAUTH_CODE");

        Long actual = 수량_쿠폰을_발급한다(senderToken, "수량 쿠폰의 내용", "00 이벤트 쿠폰", 10);

        assertThat(actual).isEqualTo(1L);
    }

    @Test
    void 발급된_수량_쿠폰의_재고를_감소시킬_수_있다() {
        String memberToken = 로그인을_한다("github", "ROOKIE_OAUTH_CODE");
        Long actual = 수량_쿠폰을_발급한다(memberToken, "수량 쿠폰의 내용", "00 이벤트 쿠폰", 10);

        수량_쿠폰의_재고를_감소한다(memberToken, actual);
    }
}
