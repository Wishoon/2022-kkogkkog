package com.woowacourse.kkogkkog.common.performance;

import java.lang.reflect.Proxy;
import java.util.Objects;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;

@Component
@Aspect
public class PerformanceAspect {

    private final PerformanceMonitor performanceMonitor;

    public PerformanceAspect(PerformanceMonitor performanceMonitor) {
        this.performanceMonitor = performanceMonitor;
    }

    @Around("execution(* javax.sql.DataSource.getConnection())")
    public Object datasource(final ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object returnValue = proceedingJoinPoint.proceed();

        if (isRequestScope()) {
            return Proxy.newProxyInstance(
                returnValue.getClass().getClassLoader(),
                returnValue.getClass().getInterfaces(),
                new ProxyConnectionHandler(returnValue, performanceMonitor));
        }
        return returnValue;
    }

    private boolean isRequestScope() {
        return Objects.nonNull(RequestContextHolder.getRequestAttributes());
    }
}
