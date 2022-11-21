package com.woowacourse.kkogkkog.extension;

import io.restassured.RestAssured;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;


public class RandomPortExtension implements BeforeEachCallback {

    @Override
    public void beforeEach(final ExtensionContext context) {
        var serverPort = SpringExtension.getApplicationContext(context).getEnvironment()
                .getProperty("local.server.port", Integer.class);

        if (serverPort == null) {
            throw new IllegalStateException("local server port cannot be null");
        }
        RestAssured.port = serverPort;
    }
}
