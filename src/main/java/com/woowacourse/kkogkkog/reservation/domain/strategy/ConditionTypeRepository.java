package com.woowacourse.kkogkkog.reservation.domain.strategy;

import java.util.Map;
import java.util.Map.Entry;
import org.springframework.stereotype.Component;

@Component
public class ConditionTypeRepository {

    private final Map<String, ConditionStrategy> conditions;

    public ConditionTypeRepository(final Map<String, ConditionStrategy> conditions) {
        this.conditions = conditions;
    }

    public ConditionStrategy getConditions(final String requestType) {
        return conditions.entrySet().stream()
            .filter(entry -> entry.getKey().contains(requestType.toLowerCase()))
            .map(Entry::getValue)
            .findAny()
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상태 변경 요청 입니다."));
    }
}
