package com.woowacourse.kkogkkog.extension;

import com.woowacourse.kkogkkog.support.DataCleaner;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

public class DataCleanerExtension implements AfterEachCallback {

    @Override
    public void afterEach(final ExtensionContext context) {
        var dataCleaner = (DataCleaner) SpringExtension.getApplicationContext(context)
                .getBean("dataCleaner");
        dataCleaner.execute();
    }
}
