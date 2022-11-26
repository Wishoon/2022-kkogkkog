package com.woowacourse.kkogkkog.coupon.domain;

import lombok.Getter;

@Getter
public enum Category {

    COFFEE("coffee"), MEAL("meal"), DRINK("drink");

    private String value;

    Category(final String value) {
        this.value = value;
    }
}
