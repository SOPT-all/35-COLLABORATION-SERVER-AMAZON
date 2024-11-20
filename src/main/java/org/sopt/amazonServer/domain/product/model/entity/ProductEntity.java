package org.sopt.amazonServer.domain.product.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.sql.Timestamp;

@Entity
@Table(name = "product")
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "is_best_seller", nullable = false)
    private boolean isBestSeller;

    @Column(name = "image", nullable = false)
    private String image;

    @Column(name = "brand", nullable = false)
    private String brand;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "rating", nullable = false)
    private float rating;

    @Column(name = "review_count", nullable = false)
    private int reviewCount;

    @Column(name = "price", nullable = false)
    private int price;

    @Column(name = "discount_rate", nullable = false)
    private int discountRate;

    @Column(name = "is_free_delivery", nullable = false)
    private boolean isFreeDelivery;

    @Column(name = "delivery_date", nullable = false)
    private Timestamp deliveryDate;

    @Column(name = "free_delivery_standard", nullable = false)
    private int freeDeliveryStandard;

    @Column(name = "sales", nullable = false)
    private int sales;

    @Column(name = "launch_date", nullable = false)
    private Timestamp launchDate;

    protected ProductEntity() {
    }

    public ProductEntity(
            boolean isBestSeller,
            String image,
            String brand,
            String name,
            float rating,
            int reviewCount,
            int price,
            Integer discountRate,
            boolean isFreeDelivery,
            Timestamp deliveryDate,
            Integer freeDeliveryStandard,
            int sales,
            Timestamp launchDate
    ) {
        this.isBestSeller = isBestSeller;
        this.image = image;
        this.brand = brand;
        this.name = name;
        this.rating = rating;
        this.reviewCount = reviewCount;
        this.price = price;
        this.discountRate = discountRate;
        this.isFreeDelivery = isFreeDelivery;
        this.deliveryDate = deliveryDate;
        this.freeDeliveryStandard = freeDeliveryStandard;
        this.sales = sales;
        this.launchDate = launchDate;
    }

    public final Long getId() {
        return id;
    }

    public final boolean getIsBestSeller() {
        return isBestSeller;
    }

    public final String getImage() {
        return image;
    }

    public final String getBrand() {
        return brand;
    }

    public final String getName() {
        return name;
    }

    public final float getRating() {
        return rating;
    }

    public final int getReviewCount() {
        return reviewCount;
    }

    public final int getPrice() {
        return price;
    }

    public final Integer getDiscountRate() {
        return discountRate;
    }

    public final boolean getIsFreeDelivery() {
        return isFreeDelivery;
    }

    public final Timestamp getDeliveryDate() {
        return deliveryDate;
    }

    public final Integer getFreeDeliveryStandard() {
        return freeDeliveryStandard;
    }

    public final int getSales() {
        return sales;
    }

    public final Timestamp getLaunchDate() {
        return launchDate;
    }
}
