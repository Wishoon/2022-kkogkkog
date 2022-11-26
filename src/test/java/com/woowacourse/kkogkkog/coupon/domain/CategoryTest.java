package com.woowacourse.kkogkkog.coupon.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

class CategoryTest {

    @ParameterizedTest
    @EnumSource(value = Category.class, names = {"COFFEE", "MEAL", "DRINK"})
    void 존재하는_카테고리로_요청을_하는경우_반환할_수_있다(final Category paramCategory) {
        assertDoesNotThrow(() -> Category.findCategory(paramCategory.getValue()));
    }

    @Test
    void 존재하지_않는_카테고리로_요청을_하는경우_예외가_발생한다() {
        assertThatThrownBy(() -> Category.findCategory("Non-existent category"))
            .isInstanceOf(IllegalArgumentException.class);
    }
}
