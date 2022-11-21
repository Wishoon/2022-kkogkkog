package com.woowacourse.kkogkkog.testClass;

import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.kkogkkog.annotation.AcceptanceTest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;

@AcceptanceTest
class AcceptanceDataCleanerTest {

    @Test
    void 각_테스트_마다_랜덤_포트_환경에서_데이터_클리너를_통해_테스트_격리를_할_수_있다() {
        Long extract = RestAssured
            .given()
            .get("/api/test")
            .then().log().all()
            .extract().body().as(Long.class);

        assertThat(extract).isEqualTo(1L);
    }
}
