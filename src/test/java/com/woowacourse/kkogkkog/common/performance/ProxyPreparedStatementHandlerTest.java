package com.woowacourse.kkogkkog.common.performance;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.sql.PreparedStatement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProxyPreparedStatementHandlerTest {

    private PreparedStatement preparedStatement;
    private PerformanceMonitor performanceMonitor;
    private ProxyPreparedStatementHandler proxyPreparedStatementHandler;

    @BeforeEach
    void setUp() {
        this.preparedStatement = mock(PreparedStatement.class);
        this.performanceMonitor = mock(PerformanceMonitor.class);
        this.proxyPreparedStatementHandler = new ProxyPreparedStatementHandler(preparedStatement, performanceMonitor);
    }

    @Test
    void Request_Scope에서_execute를_포함한_메서드가_실행되면_쿼리_횟수가_증가한다() throws Throwable {
        var executeMethod = preparedStatement.getClass().getMethod("execute");

        proxyPreparedStatementHandler.invoke(null, executeMethod, null);

        verify(performanceMonitor).addQueryCounts();
    }

    @Test
    void Request_Scope에서_execute를_포함하지_않은_메서드가_실행되면_쿼리_횟수가_증가하지_않는다() throws Throwable {
        var executeMethod = preparedStatement.getClass().getMethod("getParameterMetaData");

        proxyPreparedStatementHandler.invoke(null, executeMethod, null);

        verify(performanceMonitor, times(0)).addQueryCounts();
    }
}
