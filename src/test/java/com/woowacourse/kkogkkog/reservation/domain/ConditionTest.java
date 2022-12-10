package com.woowacourse.kkogkkog.reservation.domain;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class ConditionTest {

    @Test
    void 예악의_상태가_REQUESTED인_경우_수신자는_CANCELED로_상태를_변경할_수_있다() {
        Condition condition = Condition.REQUESTED;

        Condition actual = condition.update("CANCELED", 1L, 1L);

        assertThat(actual.getValue()).isEqualTo("CANCELED");
    }

    @Test
    void 예약의_상태가_REQUESTED인_경우에_발신자가_CANCELED로_상태를_변경하면_얘외가_발생한다() {
        Condition condition = Condition.REQUESTED;

        assertThatThrownBy(() -> condition.update("CANCELED", 2L, 1L))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @EnumSource(value = Condition.class, names = {"REJECTED", "APPROVED", "EXPIRED", "FINISHED"})
    void 예악의_상태가_REQUESTED가_아닌_경우_수신자가_CANCELED로_상태를_변경하면_예외가_발생한다(final Condition sourceType) {
        Condition condition = sourceType;

        assertThatThrownBy(() -> condition.update("CANCELED", 1L, 1L))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 예악의_상태가_REQUESTED인_경우_발신자는_REJECTED로_상태를_변경할_수_있다() {
        Condition condition = Condition.REQUESTED;

        Condition actual = condition.update("REJECTED", 2L, 1L);

        assertThat(actual.getValue()).isEqualTo("REJECTED");
    }

    @Test
    void 예약의_상태가_REQUESTED인_경우에_수신자가_REJECTED로_상태를_변경하면_얘외가_발생한다() {
        Condition condition = Condition.REQUESTED;

        assertThatThrownBy(() -> condition.update("REJECTED", 1L, 1L))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @EnumSource(value = Condition.class, names = {"REJECTED", "APPROVED", "EXPIRED", "FINISHED"})
    void 예악의_상태가_REQUESTED가_아닌_경우_발신자가_CANCELED로_상태를_변경하면_예외가_발생한다(final Condition sourceType) {
        Condition condition = sourceType;

        assertThatThrownBy(() -> condition.update("CANCELED", 2L, 1L))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 예악의_상태가_REQUESTED인_경우_발신자는_APPROVED로_상태를_변경할_수_있다() {
        Condition condition = Condition.REQUESTED;

        Condition actual = condition.update("APPROVED", 2L, 1L);

        assertThat(actual.getValue()).isEqualTo("APPROVED");
    }

    @Test
    void 예악의_상태가_REQUESTED인_경우_수신자가_APPROVED로_상태를_변경하면_얘외가_발생한다() {
        Condition condition = Condition.REQUESTED;

        assertThatThrownBy(() -> condition.update("APPROVED", 1L, 1L))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @EnumSource(value = Condition.class, names = {"REJECTED", "APPROVED", "EXPIRED", "FINISHED"})
    void 예악의_상태가_REQUESTED가_아닌_경우_발신자가_APPROVED로_상태를_변경하면_예외가_발생한다(final Condition sourceType) {
        Condition condition = sourceType;

        assertThatThrownBy(() -> condition.update("CANCELED", 2L, 1L))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 예악의_상태가_REQUESTED인_경우_EXPIRED로_상태를_변경할_수_있다() {
        Condition condition = Condition.REQUESTED;

        Condition actual = condition.update("EXPIRED", 999L, 1L);

        assertThat(actual.getValue()).isEqualTo("EXPIRED");
    }

    @ParameterizedTest
    @EnumSource(value = Condition.class, names = {"REJECTED", "APPROVED", "EXPIRED", "FINISHED"})
    void 예악의_상태가_REQUESTED가_아닌_경우_EXPIRED로_상태를_변경하면_예외가_발생한다(final Condition sourceType) {
        Condition condition = sourceType;

        assertThatThrownBy(() -> condition.update("EXPIRED", 2L, 1L))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 예악의_상태가_APPROVED인_경우_수신자는_FINISHED로_상태를_변경할_수_있다() {
        Condition condition = Condition.APPROVED;

        Condition actual = condition.update("FINISHED", 1L, 1L);

        assertThat(actual.getValue()).isEqualTo("FINISHED");
    }

    @Test
    void 예악의_상태가_REQUESTED인_경우_발신자가_FINISHED로_상태를_변경하면_얘외가_발생한다() {
        Condition condition = Condition.REQUESTED;

        assertThatThrownBy(() -> condition.update("FINISHED", 2L, 1L))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @EnumSource(value = Condition.class, names = {"CANCELED", "REJECTED", "EXPIRED", "FINISHED"})
    void 예악의_상태가_REQUESTED가_아닌_경우_수신자가_FINISHED로_상태를_변경하면_예외가_발생한다(final Condition sourceType) {
        Condition condition = sourceType;

        assertThatThrownBy(() -> condition.update("FINISHED", 1L, 1L))
            .isInstanceOf(IllegalArgumentException.class);
    }
}
