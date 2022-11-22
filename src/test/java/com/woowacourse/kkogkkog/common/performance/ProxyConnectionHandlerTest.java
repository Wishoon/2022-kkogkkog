package com.woowacourse.kkogkkog.common.performance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import java.sql.DriverManager;
import javax.sql.DataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.cglib.proxy.Proxy;

class ProxyConnectionHandlerTest {

    private PerformanceMonitor performanceMonitor;

    @BeforeEach
    void setUp() {
        this.performanceMonitor = mock(PerformanceMonitor.class);
    }

    @Test
    void prepareStatement_메서드를_실행하는_경우_프록시_객체를_반환한다() throws Throwable {
        try (final var connection = DriverManager.getConnection("jdbc:h2:mem:kkogkkog", "sa", "")) {
            var proxyConnectionHandler = new ProxyConnectionHandler(connection, performanceMonitor);
            var prepareStatementMethod = connection.getClass().getMethod("prepareStatement", String.class);

            var actual = proxyConnectionHandler.invoke(
                null, prepareStatementMethod, new Object[]{"CREATE TABLE TEST(id int)"});

            assertThat(actual).isInstanceOf(Proxy.class);
        }
    }

    @Test
    void prepareStatement_메서드를_실행하지_않는_경우_실제_객체를_반환한다() throws Throwable {
        try (final var connection = DriverManager.getConnection("jdbc:h2:mem:kkogkkog", "sa", "")) {
            var proxyConnectionHandler = new ProxyConnectionHandler(connection, performanceMonitor);
            var prepareStatementMethod = connection.getClass().getMethod("isReadOnly");

            var actual = proxyConnectionHandler.invoke(null, prepareStatementMethod, null);

            assertThat(actual).isNotInstanceOf(Proxy.class);
        }
    }
}
