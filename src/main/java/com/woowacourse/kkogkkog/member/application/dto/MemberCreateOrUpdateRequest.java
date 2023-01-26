package com.woowacourse.kkogkkog.member.application.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class MemberCreateOrUpdateRequest {

    private String providerId;
    private String email;
    private String username;
    private String profileUrl;
    private String oauthProvider;
}
