package com.woowacourse.kkogkkog.coupon.domain.repository;

import static com.woowacourse.kkogkkog.support.fixture.CouponFixture.READY_쿠폰;
import static com.woowacourse.kkogkkog.support.fixture.MemberFixture.발신자_회원;
import static com.woowacourse.kkogkkog.support.fixture.MemberFixture.수신자_회원;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.kkogkkog.annotation.RepositoryTest;
import com.woowacourse.kkogkkog.coupon.domain.repository.data.CouponMemberData;
import com.woowacourse.kkogkkog.member.domain.Member;
import com.woowacourse.kkogkkog.member.domain.MemberRepository;
import com.woowacourse.kkogkkog.member.domain.ProviderType;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;

@RepositoryTest
class CouponRepositoryTest {

    @Autowired
    private CouponRepository couponRepository;
    @Autowired
    private MemberRepository memberRepository;

    @Test
    void 회원은_보낸_쿠폰을_조회할_수_있다() {
        Long senderId = memberRepository.save(발신자_회원()).getId();
        Long receiverId = memberRepository.save(수신자_회원()).getId();
        Long otherId = memberRepository.save(추가_수신자_회원()).getId();
        couponRepository.save(READY_쿠폰(senderId, receiverId)).getId();
        couponRepository.save(READY_쿠폰(senderId, otherId)).getId();

        List<CouponMemberData> actual = couponRepository.findByMemberIdAndRequestType(senderId, "sender");

        assertThat(actual).hasSize(2);
    }

    @Test
    void 회원은_받은_쿠폰을_조회할_수_있다() {
        Long senderId = memberRepository.save(발신자_회원()).getId();
        Long receiverId = memberRepository.save(수신자_회원()).getId();
        Long otherId = memberRepository.save(추가_수신자_회원()).getId();
        couponRepository.save(READY_쿠폰(senderId, receiverId)).getId();
        couponRepository.save(READY_쿠폰(senderId, otherId)).getId();

        List<CouponMemberData> actual = couponRepository.findByMemberIdAndRequestType(receiverId, "receiver");

        assertThat(actual).hasSize(1);
    }

    @Test
    void 쿠폰을_조회할_때_RECEIVER_타입이나_SENDER_타입으로_조회하지_않은_경우_예외가_발생한다() {
        Long senderId = memberRepository.save(발신자_회원()).getId();
        Long receiverId = memberRepository.save(수신자_회원()).getId();
        Long otherId = memberRepository.save(추가_수신자_회원()).getId();
        couponRepository.save(READY_쿠폰(senderId, receiverId)).getId();
        couponRepository.save(READY_쿠폰(senderId, otherId)).getId();

        assertThatThrownBy(() -> couponRepository.findByMemberIdAndRequestType(receiverId, "not-type"))
            .isInstanceOf(InvalidDataAccessApiUsageException.class);
    }

    private static Member 추가_수신자_회원() {
        return new Member(null, "provider-other-member-id", "other-member@gmail.com",
            "other-member", "https://other-member-image.com", ProviderType.GITHUB, true);
    }
}
