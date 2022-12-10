package com.woowacourse.kkogkkog.reservation.domain;

import com.woowacourse.kkogkkog.reservation.domain.strategy.ApprovedStrategy;
import com.woowacourse.kkogkkog.reservation.domain.strategy.CanceledStrategy;
import com.woowacourse.kkogkkog.reservation.domain.strategy.ConditionStrategy;
import com.woowacourse.kkogkkog.reservation.domain.strategy.ExpiredStrategy;
import com.woowacourse.kkogkkog.reservation.domain.strategy.FinishedStrategy;
import com.woowacourse.kkogkkog.reservation.domain.strategy.RejectedStrategy;
import java.util.Arrays;
import lombok.Getter;

@Getter
public enum Condition {

    REQUESTED("REQUESTED", null),
    CANCELED("CANCELED", new CanceledStrategy()),
    REJECTED("REJECTED", new RejectedStrategy()),
    APPROVED("APPROVED", new ApprovedStrategy()),
    FINISHED("FINISHED", new FinishedStrategy()),
    EXPIRED("EXPIRED", new ExpiredStrategy());

    private final String value;
    private final ConditionStrategy conditionStrategy;

    Condition(final String value, final ConditionStrategy conditionStrategy) {
        this.value = value;
        this.conditionStrategy = conditionStrategy;
    }

    public Condition update(final String invokeType, final Long invokeMemberId, final Long senderId) {
        ConditionStrategy strategy = findCondition(invokeType).conditionStrategy;

        return strategy.update(this.name(), invokeMemberId, senderId);
    }

    private Condition findCondition(final String conditionName) {
        return Arrays.stream(Condition.values())
            .filter(it -> it.name().equals(conditionName))
            .findAny()
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상태 변경 요청 입니다."));
    }
}
