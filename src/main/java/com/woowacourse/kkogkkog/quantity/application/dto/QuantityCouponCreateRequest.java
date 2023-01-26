package com.woowacourse.kkogkkog.quantity.application.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class QuantityCouponCreateRequest {

    private String content;
    private String category;
    private int stock;
}
