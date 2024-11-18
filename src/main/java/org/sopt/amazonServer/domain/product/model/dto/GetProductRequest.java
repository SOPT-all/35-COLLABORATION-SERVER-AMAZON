package org.sopt.amazonServer.domain.product.model.dto;

public record GetProductRequest(
        Long id,
        String image,
        String brand,
        String name,
        boolean isBestSeller,
        float rating,
        int reviewCount,
        int price,
        int discountRate,
        boolean isFreeDelivery,
        String deliveryDate,
        int freeDeliveryStandard,
        boolean isInCart

) {


}
