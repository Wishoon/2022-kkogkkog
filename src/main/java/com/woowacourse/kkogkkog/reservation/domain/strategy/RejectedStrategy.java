package com.woowacourse.kkogkkog.reservation.domain.strategy;

import java.util.Objects;

public class RejectedStrategy implements ConditionStrategy {

    @Override
    public String update(final String conditionType, final Long invokeMemberId, final Long senderId) {
        if (!Objects.equals(conditionType, "REQUESTED")) {
            throw new IllegalArgumentException("거절 요청이 가능한 예약 상태가 아닙니다.");
        }
        if (Objects.equals(invokeMemberId, senderId)) {
            throw new IllegalArgumentException("거절 요청이 가능한 회원이 아닙니다.");
        }

        return "REJECTED";
    }
}
