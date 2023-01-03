package com.woowacourse.kkogkkog.acceptance.fixture;

import static com.woowacourse.kkogkkog.acceptance.AcceptanceContext.invokeGetWithToken;
import static com.woowacourse.kkogkkog.acceptance.AcceptanceContext.invokePostWithToken;
import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.kkogkkog.reservation.application.dto.request.ReservationCreateRequest;
import com.woowacourse.kkogkkog.reservation.application.dto.response.ReservationResponse;
import com.woowacourse.kkogkkog.reservation.application.dto.response.ReservationsResponse;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;

public class ReservationStepDefinition {

    public static Long 예약을_생성한다(
        final String token,
        final Long couponId,
        final LocalDateTime nowDateTime) {
        ReservationCreateRequest request = new ReservationCreateRequest(couponId, "예약 생성 메시지", nowDateTime);

        ExtractableResponse<Response> response = invokePostWithToken("/api/reservations", token, request);
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());

        return Long.valueOf(response.header("location").split("/")[3]);
    }

    public static ReservationResponse 예약을_단건_조회한다(
        final String token,
        final Long reservationId) {

        ExtractableResponse<Response> response = invokeGetWithToken("/api/reservations/" + reservationId, token);
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());

        return response.body().jsonPath().getObject(".", ReservationResponse.class);
    }

    public static ReservationsResponse 회원의_예약_목록을_조회한다(
        final String token) {

        ExtractableResponse<Response> response = invokeGetWithToken("/api/reservations", token);
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());

        return response.body().jsonPath().getObject(".", ReservationsResponse.class);
    }

    public static ReservationsResponse 예약이_잡혀있는_목록을_조회한다(
        final String token) {

        ExtractableResponse<Response> response = invokeGetWithToken("/api/reservations/approved", token);
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());

        return response.body().jsonPath().getObject(".", ReservationsResponse.class);
    }
}
