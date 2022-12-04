package com.woowacourse.kkogkkog.reservation.domain.strategy;

public interface ConditionStrategy {

    String update(final String conditionType, final Long invokeMemberId, final Long senderId);
}
