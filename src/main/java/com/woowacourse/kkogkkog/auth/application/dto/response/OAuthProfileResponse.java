package com.woowacourse.kkogkkog.auth.application.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class OAuthProfileResponse {

    private String id;
    private String email;
    private String username;
    private String profileUrl;

}
