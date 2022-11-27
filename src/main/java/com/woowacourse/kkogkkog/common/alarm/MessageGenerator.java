package com.woowacourse.kkogkkog.common.alarm;

public class MessageGenerator {

    private static final String EXCEPTION_TEMPLATE =
        "*[DATE]*\n"
            + "%s\n\n"
            + "*[REQUEST MEMBER]*\n"
            + "%s\n\n"
            + "*[ERROR LOG]*\n"
            + "%s \n%s.%s - %s\n\n"
            + "*[REQUEST INFORMATION]*\n"
            + "%s %s\n\n";

    private MessageGenerator() {
    }

    public static String generate(final ExceptionAlarmData response) {
        return extractExceptionAlarmResponse(response);
    }

    private static String extractExceptionAlarmResponse(final ExceptionAlarmData response) {
        return String.format(EXCEPTION_TEMPLATE,
            response.getCurrentTime(),
            null,
            response.getMessage(),
            response.getClassName(),
            response.getMethodName(),
            response.getLineNumber(),
            response.getMethod(),
            response.getRequestURI());
    }
}
