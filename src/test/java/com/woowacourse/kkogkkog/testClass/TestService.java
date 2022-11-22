package com.woowacourse.kkogkkog.testClass;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class TestService {

    private final TestEntityRepository testEntityRepository;

    public Long save() {
        return testEntityRepository.save(new TestEntity()).getId();
    }
}
