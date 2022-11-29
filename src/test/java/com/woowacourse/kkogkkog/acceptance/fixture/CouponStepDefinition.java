package com.woowacourse.kkogkkog.acceptance.fixture;

import static com.woowacourse.kkogkkog.acceptance.AcceptanceContext.invokeGetWithToken;
import static com.woowacourse.kkogkkog.acceptance.AcceptanceContext.invokePostWithToken;
import static com.woowacourse.kkogkkog.acceptance.AcceptanceContext.invokePutWithToken;
import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.kkogkkog.coupon.application.dto.request.CouponConditionUpdateRequest;
import com.woowacourse.kkogkkog.coupon.application.dto.request.CouponCreateRequest;
import com.woowacourse.kkogkkog.coupon.application.dto.response.CouponResponse;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import org.springframework.http.HttpStatus;

public class CouponStepDefinition {

    public static void 쿠폰을_발급한다(
        final String token,
        final List<Long> receiverIds,
        final String content,
        final String category) {
        CouponCreateRequest request = new CouponCreateRequest(receiverIds, content, category);

        ExtractableResponse<Response> response = invokePostWithToken("/api/coupons", token, request);
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    public static CouponResponse 쿠폰을_단건_조회한다(
        final String token,
        final Long couponId) {

        ExtractableResponse<Response> response = invokeGetWithToken("/api/coupons/" + couponId, token);
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());

        return response.body().jsonPath().getObject(".", CouponResponse.class);
    }

    public static void 쿠폰의_상태를_변경한다(
        final String token,
        final Long couponId,
        final String condition) {

        ExtractableResponse<Response> response = invokePutWithToken(
            "/api/coupons/" + couponId + "/condition", token, new CouponConditionUpdateRequest(condition));
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }
}
