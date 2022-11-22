package com.woowacourse.kkogkkog.common.performance;

import lombok.Getter;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Profile("test")
@Component
@RequestScope
@Getter
public class PerformanceMonitor {

    private long queryCounts = 0;
    private long queryTime = 0;

    public void addQueryCounts() {
        queryCounts++;
    }

    public void addQueryTime(final Long queryTime) {
        this.queryTime = queryTime;
    }
}
