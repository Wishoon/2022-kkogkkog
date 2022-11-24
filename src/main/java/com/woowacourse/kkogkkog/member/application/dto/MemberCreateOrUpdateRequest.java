package com.woowacourse.kkogkkog.member.application.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class MemberCreateOrUpdateRequest {

    private String providerId;
    private String email;
    private String username;
    private String profileUrl;
    private String oauthProvider;

    public MemberCreateOrUpdateRequest(final String providerId,
                                       final String email,
                                       final String username,
                                       final String profileUrl,
                                       final String oauthProvider) {
        this.providerId = providerId;
        this.email = email;
        this.username = username;
        this.profileUrl = profileUrl;
        this.oauthProvider = oauthProvider;
    }
}
