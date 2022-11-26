package com.woowacourse.kkogkkog.coupon.domain;

import java.util.Arrays;
import java.util.Objects;
import lombok.Getter;

@Getter
public enum Condition {

    READY("ready"),
    IN_PROGRESS("in_progress"),
    FINISH("finish");

    private String value;

    Condition(final String value) {
        this.value = value;
    }

    public static Condition findCondition(final String conditionName) {
        return Arrays.stream(Condition.values())
            .filter(condition -> condition.value.equals(conditionName))
            .findAny()
            .orElseThrow(IllegalArgumentException::new);
    }

    public void updateValidate(final String invokeCondition, final Long invokeMemberId, final Long senderId) {
        if (Objects.equals(this.value, READY.value)) {
            if (Objects.equals(invokeCondition, IN_PROGRESS.value) && Objects.equals(invokeMemberId, senderId)) {
                throw new IllegalArgumentException();
            }
        }

        if (Objects.equals(this.value, IN_PROGRESS.value)) {
            if (Objects.equals(invokeCondition, FINISH.value) && Objects.equals(invokeMemberId, senderId)) {
                throw new IllegalArgumentException();
            }
        }

        if (Objects.equals(this.value, FINISH.value)) {
            throw new IllegalArgumentException();
        }
    }
}
