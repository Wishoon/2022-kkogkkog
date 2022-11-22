package com.woowacourse.kkogkkog.common.performance;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Profile("test")
@RequiredArgsConstructor
@Slf4j
@Component
public class PerformanceInterceptor implements HandlerInterceptor {

    private static final String QUERY_PERFORMANCE_LOG_FORMAT = "STATUS_CODE: {}, METHOD: {}, URL: {}, QUERY_COUNT: {}, QUERY_TIME: {}";

    private final PerformanceMonitor performanceMonitor;

    @Override
    public void afterCompletion(final HttpServletRequest request,
                                final HttpServletResponse response,
                                final Object handler,
                                final Exception exception) {

        long queryCount = performanceMonitor.getQueryCounts();
        long queryTime = performanceMonitor.getQueryTime();

        log.info(QUERY_PERFORMANCE_LOG_FORMAT, response.getStatus(), request.getMethod(), request.getRequestURI(), queryCount, queryTime);
    }
}
