package com.woowacourse.kkogkkog.common.alarm;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Slf4j
@Aspect
@Component
public class SlackLoggerAspect {

    private final RequestStorage requestStorage;
    private final AlarmSender alarmSender;

    @Before("@annotation(com.woowacourse.kkogkkog.common.alarm.SlackLogger)")
    public void sendErrorLog(final JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        if (args.length != 1) {
            log.warn("Slack Logger Failed : Invalid Used");
            return;
        }

        ExceptionAlarmData response = ExceptionAlarmData.create(requestStorage.getRequest(), (Exception) args[0]);
        alarmSender.send(MessageGenerator.generate(response));
    }
}
