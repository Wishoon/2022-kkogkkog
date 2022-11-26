package com.woowacourse.kkogkkog.auth.infrastructure.token;

import lombok.Getter;

@Getter
public class MemberPayload {

    private Long memberId;
    private Boolean approval;

    public MemberPayload(final Long memberId, final boolean approval) {
        this.memberId = memberId;
        this.approval = approval;
    }
}
