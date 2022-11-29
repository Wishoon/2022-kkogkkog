package com.woowacourse.kkogkkog.auth.presentation;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@RequestScope
@Component
public class AuthContext {

    private Long memberId;
    private Boolean approval;

    public Long getMemberId() {
        if (memberId == null) {
            throw new IllegalArgumentException();
        }
        return memberId;
    }

    public Boolean getApproval() {
        if (approval == null) {
            throw new IllegalArgumentException();
        }
        return approval;
    }

    public void setMemberId(final Long memberId) {
        this.memberId = memberId;
    }

    public void setApproval(final Boolean approval) {
        this.approval = approval;
    }
}
