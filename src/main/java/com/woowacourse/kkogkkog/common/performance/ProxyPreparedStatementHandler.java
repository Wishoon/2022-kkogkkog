package com.woowacourse.kkogkkog.common.performance;

import java.lang.reflect.Method;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.proxy.InvocationHandler;

@RequiredArgsConstructor
public class ProxyPreparedStatementHandler implements InvocationHandler {

    private final Object preparedStatement;
    private final PerformanceMonitor performanceMonitor;

    @Override
    public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
        if (isExecuteQuery(method)) {
            long startTime = System.currentTimeMillis();
            Object returnValue = method.invoke(preparedStatement, args);
            performanceMonitor.addQueryTime(System.currentTimeMillis() - startTime);
            performanceMonitor.addQueryCounts();
            return returnValue;
        }

        return method.invoke(preparedStatement, args);
    }

    private boolean isExecuteQuery(final Method method) {
        String methodName = method.getName();
        return methodName.equals("executeQuery") || methodName.equals("execute") || methodName.equals("executeUpdate");
    }
}
