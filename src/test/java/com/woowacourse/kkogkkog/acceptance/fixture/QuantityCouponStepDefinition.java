package com.woowacourse.kkogkkog.acceptance.fixture;

import static com.woowacourse.kkogkkog.acceptance.AcceptanceContext.invokePostWithToken;
import static com.woowacourse.kkogkkog.acceptance.AcceptanceContext.invokePutWithToken;
import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.kkogkkog.quantity.application.dto.QuantityCouponCreateRequest;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.HttpStatus;

public class QuantityCouponStepDefinition {

    private static final String COUPON_KEY_PREFIX = "QUANTITY_COUPON_";

    public static Long 수량_쿠폰을_발급한다(
        final String token,
        final String content,
        final String category,
        final int stock) {
        QuantityCouponCreateRequest request = new QuantityCouponCreateRequest(content, category, stock);

        ExtractableResponse<Response> response = invokePostWithToken("/api/quantity-coupons", token, request);
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());

        return Long.valueOf(response.header("location").split("/")[3]);
    }

    public static void 수량_쿠폰의_재고를_감소한다(
        final String token,
        final Long quantityCouponId) {

        ExtractableResponse<Response> response = invokePutWithToken(
            "/api/quantity-coupons/" + quantityCouponId + "/stock", token);
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }
}
