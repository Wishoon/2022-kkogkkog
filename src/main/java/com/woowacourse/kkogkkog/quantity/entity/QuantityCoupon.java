package com.woowacourse.kkogkkog.quantity.entity;

import com.woowacourse.kkogkkog.common.base.BaseEntity;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class QuantityCoupon extends BaseEntity {

    private static final int MINIMUM_DEFAULT_QUANTITY_COUPON_STOCK = 0;
    private static final int MINIMUM_DECREASE_QUANTITY_COUPON_STOCK = 0;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "serial_number", columnDefinition = "BINARY(16)", nullable = false, unique = true)
    private UUID serialNumber;

    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "category", nullable = false)
    private String category;

    @Column(name = "stock", nullable = false)
    @ColumnDefault("1")
    private int stock;

    @Builder
    public QuantityCoupon(final Long id,
                          final UUID serialNumber,
                          final Long memberId,
                          final String content,
                          final String category,
                          final int stock) {
        validateStockCount(stock);
        this.id = id;
        this.serialNumber = serialNumber;
        this.memberId = memberId;
        this.content = content;
        this.category = category;
        this.stock = stock;
    }

    private void validateStockCount(final int stock) {
        if (stock < MINIMUM_DEFAULT_QUANTITY_COUPON_STOCK) {
            throw new IllegalArgumentException();
        }
    }

    public void decreaseStock() {
        validateDecreaseStockCount();
        this.stock -= 1;
    }

    private void validateDecreaseStockCount() {
        if (stock <= MINIMUM_DECREASE_QUANTITY_COUPON_STOCK) {
            throw new IllegalArgumentException();
        }
    }
}
