package com.woowacourse.kkogkkog.reservation.domain.strategy;

import com.woowacourse.kkogkkog.reservation.domain.Condition;
import java.util.Objects;

public class FinishedStrategy implements ConditionStrategy {

    @Override
    public Condition update(final String conditionType, final Long invokeMemberId, final Long senderId) {
        if (!Objects.equals(conditionType, Condition.APPROVED.getValue())) {
            throw new IllegalArgumentException("완료 요청이 가능한 예약 상태가 아닙니다.");
        }
        if (!Objects.equals(invokeMemberId, senderId)) {
            throw new IllegalArgumentException("취소 요청이 가능한 회원이 아닙니다.");
        }

        return Condition.FINISHED;
    }
}
