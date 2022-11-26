package com.woowacourse.kkogkkog.member.application;

import com.woowacourse.kkogkkog.member.application.dto.MemberCreateOrUpdateRequest;
import com.woowacourse.kkogkkog.member.domain.Member;
import com.woowacourse.kkogkkog.member.domain.MemberRepository;
import com.woowacourse.kkogkkog.member.domain.ProviderType;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public Long createOrUpdate(final MemberCreateOrUpdateRequest request) {
        Optional<Member> findMember = memberRepository.findByEmailAndOauthProviderType(
            request.getEmail(), ProviderType.findProvider(request.getOauthProvider()));
        if (findMember.isPresent()) {
            Member member = findMember.get();
            return member.update(request.getUsername(), request.getProfileUrl()).getId();
        }

        return memberRepository.save(createMember(request)).getId();
    }

    public boolean findApproval(final Long memberId) {
        Member member = memberRepository.getById(memberId);

        return member.getApproval();
    }

    private static Member createMember(final MemberCreateOrUpdateRequest request) {
        return Member.builder()
            .providerId(request.getProviderId())
            .email(request.getEmail())
            .username(request.getUsername())
            .imageUrl(request.getProfileUrl())
            .providerType(ProviderType.findProvider(request.getOauthProvider()))
            .build();
    }
}
