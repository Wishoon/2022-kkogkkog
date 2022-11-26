package com.woowacourse.kkogkkog.coupon.domain;

import com.woowacourse.kkogkkog.common.base.BaseEntity;
import java.util.Objects;
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
public class Coupon extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sender_id", nullable = false)
    private Long senderId;

    @Column(name = "receiver_id", nullable = false)
    private Long receiverId;

    @Column(name = "content", nullable = false)
    private String content;

    @Enumerated(EnumType.STRING)
    private Category category;

    @Enumerated(EnumType.STRING)
    private Condition condition;

    @Builder
    public Coupon(final Long id,
                  final Long senderId,
                  final Long receiverId,
                  final String content,
                  final Category category,
                  final Condition condition) {
        validate(senderId, receiverId);
        this.id = id;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.content = content;
        this.category = category;
        this.condition = condition;
    }

    private void validate(final Long senderId, final Long receiverId) {
        if (Objects.equals(senderId, receiverId)) {
            throw new IllegalArgumentException();
        }
    }

    public Coupon updateCondition(final String invokeCondition, final Long invokeMemberId) {
        condition.updateValidate(invokeCondition, invokeMemberId, senderId);

        condition = Condition.findCondition(invokeCondition);
        return new Coupon(id, senderId, receiverId, content, category, condition);
    }
}
