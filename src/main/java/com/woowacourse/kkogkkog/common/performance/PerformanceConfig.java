package com.woowacourse.kkogkkog.common.performance;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@RequiredArgsConstructor
@Configuration
public class PerformanceConfig implements WebMvcConfigurer {

    private final PerformanceMonitor performanceMonitor;

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(new PerformanceInterceptor(performanceMonitor));
    }
}
