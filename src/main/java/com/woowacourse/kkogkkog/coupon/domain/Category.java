package com.woowacourse.kkogkkog.coupon.domain;

import java.util.Arrays;
import lombok.Getter;

@Getter
public enum Category {

    COFFEE("coffee"), MEAL("meal"), DRINK("drink"), EVENT("event");

    private String value;

    Category(final String value) {
        this.value = value;
    }

    public static Category findCategory(final String categoryName) {
        return Arrays.stream(Category.values())
            .filter(category -> category.value.equals(categoryName))
            .findAny()
            .orElseThrow(IllegalArgumentException::new);
    }
}
