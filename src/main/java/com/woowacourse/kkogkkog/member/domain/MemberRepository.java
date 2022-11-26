package com.woowacourse.kkogkkog.member.domain;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberRepository extends JpaRepository<Member, Long> {

    default Member getById(final Long couponId) {
        return findById(couponId)
            .orElseThrow(IllegalArgumentException::new);
    }

    @Query("select m from Member m where m.email = :email and m.providerType = :providerType")
    Optional<Member> findByEmailAndOauthProviderType(@Param("email") String email,
                                                     @Param("providerType") ProviderType providerType);

    boolean existsById(Long memberId);
}
