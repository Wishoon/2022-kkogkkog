package com.woowacourse.kkogkkog.common.alarm;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class SlackAlarmSender implements AlarmSender {

    private static final String REQUEST_URI = "https://hooks.slack.com/services/";

    @Value("${alarm.slack.exception.hook-url}")
    private String hookUri;

    @Override
    public void send(final String message) {
        WebClient.create(REQUEST_URI)
            .post()
            .uri(hookUri)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(new MessageRequest(message))
            .retrieve()
            .bodyToMono(Void.class)
            .subscribe();
    }

    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Getter
    public static class MessageRequest {

        private String text;
    }
}
