package com.woowacourse.kkogkkog.common.alarm;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.util.ContentCachingRequestWrapper;

@AllArgsConstructor
@Getter
public class ExceptionAlarmData {

    private final String method;
    private final String requestURI;
    private final String className;
    private final String methodName;
    private final int lineNumber;
    private final String message;
    private final String currentTime;

    public static ExceptionAlarmData create(final ContentCachingRequestWrapper request, final Exception exception) {
        StackTraceElement[] stackTrace = exception.getStackTrace();
        return new ExceptionAlarmData(
            request.getMethod(),
            request.getRequestURI(),
            stackTrace[0].getClassName(),
            stackTrace[0].getMethodName(),
            stackTrace[0].getLineNumber(),
            exception.toString(),
            LocalDateTime.now().toString());
    }
}
