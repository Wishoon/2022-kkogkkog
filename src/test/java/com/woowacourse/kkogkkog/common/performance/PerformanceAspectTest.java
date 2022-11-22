package com.woowacourse.kkogkkog.common.performance;

import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.kkogkkog.annotation.IntegrationTest;
import java.lang.reflect.Proxy;
import javax.sql.DataSource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;

@IntegrationTest
class PerformanceAspectTest {

    @Autowired
    private DataSource dataSource;

    @Test
    void DataSource의_getConnection을_요청하면_프록시_객체를_반환한다() throws Exception {
        try (final var connection = dataSource.getConnection()) {
            assertThat(connection).isInstanceOf(Proxy.class);
        }
    }

    @Test
    void DataSource의_getConnection을_요청할_때_Request_Scope가_존재해야_한다() throws Exception {
        try (final var connection = dataSource.getConnection()) {
            assertThat(RequestContextHolder.getRequestAttributes()).isNotNull();
            assertThat(connection).isInstanceOf(Proxy.class);
        }
    }
}
