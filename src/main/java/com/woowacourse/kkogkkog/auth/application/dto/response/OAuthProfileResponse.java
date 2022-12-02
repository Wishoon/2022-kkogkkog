package com.woowacourse.kkogkkog.auth.application.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class OAuthProfileResponse {

    private String id;
    private String email;
    private String username;
    private String profileUrl;

    public OAuthProfileResponse(final String id, final String email, final String username, final String profileUrl) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.profileUrl = profileUrl;
    }
}
