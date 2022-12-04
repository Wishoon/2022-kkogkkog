package com.woowacourse.kkogkkog.reservation.domain.strategy;

import java.util.Objects;

public class ExpiredStrategy implements ConditionStrategy {

    @Override
    public String update(final String conditionType, final Long invokeMemberId, final Long senderId) {
        if (!Objects.equals(conditionType, "REQUESTED")) {
            throw new IllegalArgumentException("만료 요청이 가능한 예약 상태가 아닙니다.");
        }

        return "EXPIRED";
    }
}
