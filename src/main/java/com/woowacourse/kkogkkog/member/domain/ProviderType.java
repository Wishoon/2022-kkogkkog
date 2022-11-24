package com.woowacourse.kkogkkog.member.domain;

import java.util.Arrays;
import lombok.Getter;

@Getter
public enum ProviderType {

    GITHUB("github");

    private String provider;

    ProviderType(final String provider) {
        this.provider = provider;
    }

    public static ProviderType findProvider(final String providerName) {
        return Arrays.stream(ProviderType.values())
            .filter(value -> value.provider.equals(providerName))
            .findAny()
            .orElseThrow(IllegalArgumentException::new);
    }
}
