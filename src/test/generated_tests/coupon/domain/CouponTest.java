package coupon.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.kkogkkog.coupon.domain.Category;
import com.woowacourse.kkogkkog.coupon.domain.Condition;
import com.woowacourse.kkogkkog.coupon.domain.Coupon;
import org.junit.jupiter.api.Test;

class CouponTest {

    private final Long senderId = 1L;
    private final Long receiverId = 2L;

    @Test
    void 쿠폰_생성시_SENDER와_RECEIVER가_동일할_경우_예외가_발생한다() {
        assertThatThrownBy(() -> new Coupon(null, 1L, 1L, "쿠폰의 내용", Category.COFFEE, Condition.READY))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 쿠폰의_상태가_READY일_때_RECEIVER는_쿠폰의_상태를_IN_PROGRESS로_변경할_수_있다() {
        Coupon coupon = new Coupon(null, senderId, receiverId, "쿠폰의 내용", Category.COFFEE, Condition.READY);

        Coupon actual = coupon.updateCondition(Condition.IN_PROGRESS.getValue(), receiverId);

        assertThat(actual.getCondition()).isEqualTo(Condition.IN_PROGRESS);
    }

    @Test
    void 쿠폰의_상태가_READY일_때_RECEIVER는_쿠폰의_상태를_FINISH로_변경할_수_있다() {
        Coupon coupon = new Coupon(null, senderId, receiverId, "쿠폰의 내용", Category.COFFEE, Condition.READY);

        Coupon actual = coupon.updateCondition(Condition.FINISH.getValue(), receiverId);

        assertThat(actual.getCondition()).isEqualTo(Condition.FINISH);
    }

    @Test
    void 쿠폰의_상태가_IN_PROGRESS일_때_RECEIVER는_쿠폰의_상태를_READY로_변경할_수_있다() {
        Coupon coupon = new Coupon(null, senderId, receiverId, "쿠폰의 내용", Category.COFFEE, Condition.IN_PROGRESS);

        Coupon actual = coupon.updateCondition(Condition.READY.getValue(), receiverId);

        assertThat(actual.getCondition()).isEqualTo(Condition.READY);
    }

    @Test
    void 쿠폰의_상태가_IN_PROGRESS일_때_RECEIVER는_쿠폰의_상태를_FINISH로_변경할_수_있다() {
        Coupon coupon = new Coupon(null, senderId, receiverId, "쿠폰의 내용", Category.COFFEE, Condition.IN_PROGRESS);

        Coupon actual = coupon.updateCondition(Condition.FINISH.getValue(), receiverId);

        assertThat(actual.getCondition()).isEqualTo(Condition.FINISH);
    }

    @Test
    void 쿠폰의_상태가_IN_PROGRESS일_때_SENDER는_쿠폰의_상태를_READY로_변경할_수_있다() {
        Coupon coupon = new Coupon(null, senderId, receiverId, "쿠폰의 내용", Category.COFFEE, Condition.IN_PROGRESS);

        Coupon actual = coupon.updateCondition(Condition.READY.getValue(), senderId);

        assertThat(actual.getCondition()).isEqualTo(Condition.READY);
    }
}
