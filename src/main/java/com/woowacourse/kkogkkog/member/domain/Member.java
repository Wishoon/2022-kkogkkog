package com.woowacourse.kkogkkog.member.domain;

import com.woowacourse.kkogkkog.common.base.BaseEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "provider_id", nullable = false, unique = true)
    private String providerId;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "imageUrl", nullable = false)
    private String imageUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "provider_type", nullable = false)
    private ProviderType providerType;

    @Column(name = "approval")
    private boolean approval;

    @Builder
    public Member(final Long id,
                  final String providerId,
                  final String email,
                  final String username,
                  final String imageUrl,
                  final ProviderType providerType,
                  final boolean approval) {
        this.id = id;
        this.providerId = providerId;
        this.email = email;
        this.username = username;
        this.imageUrl = imageUrl;
        this.providerType = providerType;
        this.approval = approval;
    }

    public Member update(final String username, final String imageUrl) {
        this.username = username;
        this.imageUrl = imageUrl;
        return new Member(id, providerId, email, username, imageUrl, providerType, approval);
    }
}
