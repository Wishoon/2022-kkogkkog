package com.woowacourse.kkogkkog.reservation.domain;

import com.woowacourse.kkogkkog.reservation.domain.strategy.ConditionStrategy;
import com.woowacourse.kkogkkog.reservation.domain.strategy.ConditionTypeRepository;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Condition {

    @Column(name = "condition", nullable = false)
    private String condition;
    @Transient
    private ConditionTypeRepository conditionTypeRepository;

    public Condition(final String condition, final ConditionTypeRepository conditionTypeRepository) {
        this.condition = condition;
        this.conditionTypeRepository = conditionTypeRepository;
    }

    public Condition update(final String invokeType, final Long invokeMemberId, final Long senderId) {
        ConditionStrategy conditionStrategy = conditionTypeRepository.getConditions(invokeType);

        String condition = conditionStrategy.update(this.condition, invokeMemberId, senderId);

        return new Condition(condition, conditionTypeRepository);
    }
}
