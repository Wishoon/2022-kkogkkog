package com.woowacourse.kkogkkog.auth.presentation;

import com.woowacourse.kkogkkog.auth.infrastructure.token.MemberPayload;
import com.woowacourse.kkogkkog.auth.infrastructure.token.TokenExtractor;
import com.woowacourse.kkogkkog.auth.infrastructure.token.TokenProvider;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.servlet.HandlerInterceptor;

@RequiredArgsConstructor
@Component
public class AuthInterceptor implements HandlerInterceptor {

    private final TokenExtractor tokenExtractor;
    private final TokenProvider tokenProvider;
    private final AuthContext authContext;

    @Override
    public boolean preHandle(final HttpServletRequest request,
                             final HttpServletResponse response,
                             final Object handler) throws Exception {
        if (CorsUtils.isPreFlightRequest(request)) {
            return true;
        }

        String accessToken = tokenExtractor.extractToken(request.getHeader(HttpHeaders.AUTHORIZATION), "Bearer");
        MemberPayload memberPayLoad = tokenProvider.getPayload(accessToken);

        // approval 검증로직을 추후 활용
        authContext.setMemberId(memberPayLoad.getMemberId());
        return true;
    }
}
