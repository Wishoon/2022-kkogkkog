package com.woowacourse.kkogkkog.member.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class ProviderTypeTest {

    @Test
    void ProviderType에_해당되는_이름이_존재하는_경우_ProviderType을_반환할_수_있다() {
        ProviderType actual = ProviderType.findProvider("github");

        assertThat(actual).isInstanceOf(ProviderType.class);
    }

    @Test
    void ProviderType에_해당되는_이름이_존재하지_않는경우_예외가_발생한다() {
        assertThatThrownBy(() -> ProviderType.findProvider("notProvider"))
            .isInstanceOf(IllegalArgumentException.class);
    }
}
