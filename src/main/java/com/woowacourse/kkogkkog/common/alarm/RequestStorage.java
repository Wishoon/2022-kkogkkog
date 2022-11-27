package com.woowacourse.kkogkkog.common.alarm;

import lombok.Getter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.util.ContentCachingRequestWrapper;

@Getter
@Component
@RequestScope
public class RequestStorage {

    private ContentCachingRequestWrapper request;

    public void set(ContentCachingRequestWrapper request) {
        this.request = request;
    }
}
