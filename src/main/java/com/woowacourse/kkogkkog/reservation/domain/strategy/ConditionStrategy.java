package com.woowacourse.kkogkkog.reservation.domain.strategy;

import com.woowacourse.kkogkkog.reservation.domain.Condition;

public interface ConditionStrategy {

    Condition update(final String conditionType, final Long invokeMemberId, final Long senderId);
}
