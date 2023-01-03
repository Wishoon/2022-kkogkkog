package com.woowacourse.kkogkkog.acceptance;

import static com.woowacourse.kkogkkog.acceptance.fixture.CouponStepDefinition.쿠폰을_발급한다;
import static com.woowacourse.kkogkkog.acceptance.fixture.MemberStepDefinition.로그인을_한다;
import static com.woowacourse.kkogkkog.acceptance.fixture.ReservationStepDefinition.예약을_단건_조회한다;
import static com.woowacourse.kkogkkog.acceptance.fixture.ReservationStepDefinition.예약을_생성한다;
import static com.woowacourse.kkogkkog.acceptance.fixture.ReservationStepDefinition.회원의_예약_목록을_조회한다;
import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.kkogkkog.annotation.AcceptanceTest;
import com.woowacourse.kkogkkog.coupon.domain.Category;
import com.woowacourse.kkogkkog.reservation.application.dto.response.ReservationResponse;
import com.woowacourse.kkogkkog.reservation.application.dto.response.ReservationsResponse;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;

@AcceptanceTest
public class ReservationAcceptanceTest {

    @Test
    void 예약을_생성할_수_있다() {
        String senderToken = 로그인을_한다("github", "ROOKIE_OAUTH_CODE");
        String receiverToken = 로그인을_한다("github", "ROMA_OAUTH_CODE");
        쿠폰을_발급한다(senderToken, List.of(2L), "쿠폰의 내용", Category.COFFEE.getValue());

        Long actual = 예약을_생성한다(receiverToken, 1L, LocalDateTime.now().plusDays(1));

        assertThat(actual).isEqualTo(1);
    }

    @Test
    void 예약을_조회할_수_있다() {
        String senderToken = 로그인을_한다("github", "ROOKIE_OAUTH_CODE");
        String receiverToken = 로그인을_한다("github", "ROMA_OAUTH_CODE");
        쿠폰을_발급한다(senderToken, List.of(2L), "쿠폰의 내용", Category.COFFEE.getValue());
        Long reservationId = 예약을_생성한다(receiverToken, 1L, LocalDateTime.now().plusDays(1));

        ReservationResponse actual = 예약을_단건_조회한다(receiverToken, reservationId);

        assertThat(actual.getReservationId()).isEqualTo(1L);
    }

    @Test
    void 회원의_예약_목록들을_조회할_수_있다() {
        String senderToken = 로그인을_한다("github", "ROOKIE_OAUTH_CODE");
        String receiverToken = 로그인을_한다("github", "ROMA_OAUTH_CODE");
        쿠폰을_발급한다(senderToken, List.of(2L), "쿠폰의 내용", Category.COFFEE.getValue());
        쿠폰을_발급한다(senderToken, List.of(2L), "쿠폰의 내용", Category.COFFEE.getValue());
        예약을_생성한다(receiverToken, 1L, LocalDateTime.now().plusDays(1));
        예약을_생성한다(receiverToken, 2L, LocalDateTime.now().plusDays(1));

        ReservationsResponse actual = 회원의_예약_목록을_조회한다(receiverToken);
        assertThat(actual.getData()).hasSize(2);
    }
}
