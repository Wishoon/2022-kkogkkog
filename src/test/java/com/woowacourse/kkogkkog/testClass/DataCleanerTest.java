package com.woowacourse.kkogkkog.testClass;

import com.woowacourse.kkogkkog.annotation.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

@IntegrationTest
class DataCleanerTest {

    @Autowired
    private TestEntityRepository testEntityRepository;

    @Test
    void 각_테스트_마다_데이터_클리너를_통해_테스트_격리를_할_수_있다() {
        testEntityRepository.save(new TestEntity(null));

        assertThat(testEntityRepository.findAll().size()).isEqualTo(1);
    }
}
