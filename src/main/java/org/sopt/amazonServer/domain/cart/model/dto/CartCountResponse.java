package org.sopt.amazonServer.domain.cart.model.dto;

public record CartCountResponse(
        int cartCount
) {

    public static CartCountResponse fromCartCount(int cartCount) {
        if (cartCount < 0) {
            throw new IllegalArgumentException("Cart count는 양수일 수 없습니다.");
        }
        return new CartCountResponse(cartCount);
    }
}