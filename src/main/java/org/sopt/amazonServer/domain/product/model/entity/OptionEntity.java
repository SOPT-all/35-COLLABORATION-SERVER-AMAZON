package org.sopt.amazonServer.domain.product.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;

@Entity
@Table(
        name = "option",
        indexes = {
                @Index(name = "idx_option_category_id", columnList = "category_id")
        }
)
public class OptionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "category_id", nullable = false)
    private long categoryId;

    @Column(name = "name", nullable = false)
    private String name;

    protected OptionEntity() {
    }

    public Long getId() {
        return id;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public String getName() {
        return name;
    }
}
