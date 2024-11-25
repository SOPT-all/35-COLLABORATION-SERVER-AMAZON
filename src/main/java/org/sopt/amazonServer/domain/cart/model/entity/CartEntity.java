package org.sopt.amazonServer.domain.cart.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(
        name = "cart",
        uniqueConstraints = {
                @UniqueConstraint(name = "unique_cart_member_id_product_id", columnNames = {"member_id", "product_id"})
        }
)
public class CartEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @Column(name = "product_id", nullable = false)
    private Long productId;

    protected CartEntity() {
    }

    public CartEntity(final Long memberId, final Long productId) {
        this.memberId = memberId;
        this.productId = productId;
    }

    public Long getProductId() {
        return productId;
    }
}
