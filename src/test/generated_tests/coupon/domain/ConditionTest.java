package coupon.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import com.woowacourse.kkogkkog.coupon.domain.Condition;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

class ConditionTest {

    @ParameterizedTest
    @EnumSource(value = Condition.class, names = {"READY", "IN_PROGRESS", "FINISH"})
    void 존재하는_쿠폰의_상태로_요청을_하는경우_반환할_수_있다(final Condition paramCondition) {
        assertDoesNotThrow(() -> Condition.findCondition(paramCondition.getValue()));
    }

    @Test
    void 존재하지_않는_쿠폰의_상태로_요청을_하는경우_예외가_발생한다() {
        assertThatThrownBy(() -> Condition.findCondition("Non-existent condition"))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 쿠폰의_상태가_READY를_때_SENDER가_IN_PROGRESS로_쿠폰_상태를_변경하면_예외가_발생한다() {
        Condition condition = Condition.READY;

        assertThatThrownBy(() -> condition.updateValidate(Condition.IN_PROGRESS.getValue(), 1L, 1L));
    }

    @Test
    void 쿠폰의_상태가_IN_PROGRESS일_때_SENDER가_FINISH로_쿠폰_상태를_변경하면_예외가_발생한다() {
        Condition condition = Condition.IN_PROGRESS;

        assertThatThrownBy(() -> condition.updateValidate(Condition.FINISH.getValue(), 1L, 1L));
    }

    @ParameterizedTest
    @EnumSource(value = Condition.class, names = {"READY", "IN_PROGRESS", "FINISH"})
    void 쿠폰의_상태가_FINISH일_때_쿠폰_상태를_변경하면_예외가_발생한다(final Condition paramCondition) {
        Condition condition = Condition.FINISH;

        assertThatThrownBy(() -> condition.updateValidate(paramCondition.getValue(), 1L, 1L));
    }
}
