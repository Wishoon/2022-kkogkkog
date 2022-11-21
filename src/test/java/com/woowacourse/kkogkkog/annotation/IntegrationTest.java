package com.woowacourse.kkogkkog.annotation;

import com.woowacourse.kkogkkog.extension.DataCleanerExtension;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Retention(RetentionPolicy.RUNTIME)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@ExtendWith(DataCleanerExtension.class)
public @interface IntegrationTest {
}
