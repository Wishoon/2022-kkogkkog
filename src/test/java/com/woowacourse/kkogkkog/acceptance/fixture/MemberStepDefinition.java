package com.woowacourse.kkogkkog.acceptance.fixture;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.woowacourse.kkogkkog.auth.application.dto.request.AccessTokenRequest;
import com.woowacourse.kkogkkog.auth.application.dto.response.AccessTokenResponse;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.HttpStatus;

public class MemberStepDefinition {

    public static String 로그인을_한다(
        final String oauthProvider,
        final String code) {

        ExtractableResponse<Response> response = RestAssured.given().log().all()
            .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
            .body(new AccessTokenRequest(code))
            .when().log().all()
            .post("/api/auth/" + oauthProvider + "/token")
            .then().log().all()
            .statusCode(HttpStatus.OK.value())
            .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        return response.body().jsonPath().getObject(".", AccessTokenResponse.class).getAccessToken();
    }
}
