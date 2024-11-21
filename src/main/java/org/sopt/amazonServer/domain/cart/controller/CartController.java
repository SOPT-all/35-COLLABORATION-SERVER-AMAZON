package org.sopt.amazonServer.domain.cart.controller;

import jakarta.validation.constraints.Positive;
import org.sopt.amazonServer.domain.cart.model.dto.CartResponse;
import org.sopt.amazonServer.domain.cart.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/api/v1/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @DeleteMapping("/{productId}")
    ResponseEntity<CartResponse> deleteProductInCart(
            @Positive(message = "productId는 양수여야 합니다.")
            @PathVariable(name = "productId") final Long productId, // TODO: productId 없을 때 500 에러 막기
            @RequestHeader("memberId") final Long memberId
    ) {
        return ResponseEntity.ok(cartService.removeProductInCart(productId, memberId));
    }

    @PostMapping("/{productId}")
    ResponseEntity<CartResponse> postProductInCart(
            @Positive(message = "productId는 양수여야 합니다.")
            @PathVariable(name = "productId") final Long productId, // TODO: productId 없을 때 500 에러 막기
            @RequestHeader("memberId") final Long memberId
    ) {
        return ResponseEntity.ok(cartService.createProductInCart(productId, memberId));
    }

}
