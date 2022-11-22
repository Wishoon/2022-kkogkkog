package com.woowacourse.kkogkkog.common.performance;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.proxy.Proxy;

@RequiredArgsConstructor
public class ProxyConnectionHandler implements InvocationHandler {

    private final Object connection;
    private final PerformanceMonitor performanceMonitor;

    @Override
    public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
        Object returnValue = method.invoke(connection, args);

        if (method.getName().equals("prepareStatement")) {
            return Proxy.newProxyInstance(
                returnValue.getClass().getClassLoader(),
                returnValue.getClass().getInterfaces(),
                new ProxyPreparedStatementHandler(returnValue, performanceMonitor));
        }

        return returnValue;
    }
}
