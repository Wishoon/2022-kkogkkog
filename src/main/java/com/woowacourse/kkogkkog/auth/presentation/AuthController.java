package com.woowacourse.kkogkkog.auth.presentation;

import com.woowacourse.kkogkkog.auth.application.AuthService;
import com.woowacourse.kkogkkog.auth.application.OAuthService;
import com.woowacourse.kkogkkog.auth.application.dto.request.AccessTokenRequest;
import com.woowacourse.kkogkkog.auth.application.dto.response.AccessTokenResponse;
import com.woowacourse.kkogkkog.auth.application.dto.response.OAuthProfileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final OAuthService oAuthService;
    private final AuthService authService;

    @PostMapping("/{oauthProvider}/token")
    public ResponseEntity<AccessTokenResponse> createAccessToken(@PathVariable final String oauthProvider,
                                                                 @RequestBody final AccessTokenRequest request) {
        OAuthProfileResponse oauthProfile = oAuthService.getOauthProfile(oauthProvider, request);
        AccessTokenResponse tokenResponse = authService.createAccessToken(oauthProvider, oauthProfile);

        return ResponseEntity.ok(tokenResponse);
    }
}
