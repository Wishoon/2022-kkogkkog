package com.woowacourse.kkogkkog.support.fixture;

import com.woowacourse.kkogkkog.member.application.dto.MemberCreateOrUpdateRequest;
import com.woowacourse.kkogkkog.member.domain.Member;
import com.woowacourse.kkogkkog.member.domain.ProviderType;
import lombok.Getter;

@Getter
public enum MemberFixture {

    회원("provider-no-auth-member-id", "no-auth-member@gmail.com", "no-auth-member",
        "https://no-auth-member-image.com", ProviderType.GITHUB, false),
    인증된_회원("provider-auth-member-id", "auth-member@gmail.com", "auth-member", "https://auth-member-image.com",
        ProviderType.GITHUB, true),
    발신자_회원("provider-sender-member-id", "sender-member@gmail.com", "sender-member", "https://sender-member-image.com",
        ProviderType.GITHUB, true),
    수신자_회원("provider-receiver-member-id", "receiver-member@gmail.com", "receiver-member",
        "https://receiver-member-image.com",
        ProviderType.GITHUB, true);

    private String providerId;
    private String email;
    private String username;
    private String imageUrl;
    private ProviderType providerType;
    private Boolean approval;

    MemberFixture(final String providerId,
                  final String email,
                  final String username,
                  final String imageUrl,
                  final ProviderType providerType,
                  final Boolean approval) {
        this.providerId = providerId;
        this.email = email;
        this.username = username;
        this.imageUrl = imageUrl;
        this.providerType = providerType;
        this.approval = approval;
    }

    // 회원 도메인
    public static Member 회원() {
        return new Member(
            null, 회원.providerId, 회원.email, 회원.username,
            회원.imageUrl, 회원.providerType, 회원.approval);
    }

    public static Member 인증된_회원() {
        return new Member(
            null, 인증된_회원.providerId, 인증된_회원.email, 인증된_회원.username,
            인증된_회원.imageUrl, 인증된_회원.providerType, 인증된_회원.approval);
    }

    public static Member 발신자_회원() {
        return new Member(
            null, 발신자_회원.providerId, 발신자_회원.email, 발신자_회원.username,
            발신자_회원.imageUrl, 발신자_회원.providerType, 발신자_회원.approval);
    }

    public static Member 수신자_회원() {
        return new Member(
            null, 수신자_회원.providerId, 수신자_회원.email, 수신자_회원.username,
            수신자_회원.imageUrl, 수신자_회원.providerType, 수신자_회원.approval);
    }

    // 회원 생성 요청
    public static MemberCreateOrUpdateRequest 회원을_생성하거나_변경하는_요청() {
        return new MemberCreateOrUpdateRequest(
            회원.providerId, 회원.email, 회원.username,
            회원.imageUrl, 회원.providerType.getProvider());
    }
}
