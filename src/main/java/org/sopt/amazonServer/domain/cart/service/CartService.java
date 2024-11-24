package org.sopt.amazonServer.domain.cart.service;

import jakarta.transaction.Transactional;
import org.sopt.amazonServer.domain.cart.model.dto.CartCountResponse;
import org.sopt.amazonServer.domain.cart.model.entity.CartEntity;
import org.sopt.amazonServer.domain.cart.repostiory.CartRepository;
import org.sopt.amazonServer.domain.product.repository.ProductRepository;
import org.sopt.amazonServer.global.exception.BusinessException;
import org.sopt.amazonServer.global.exception.ErrorType;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    public CartService(CartRepository cartRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public CartCountResponse removeProductInCart(Long productId, Long memberId) {
        if (!productRepository.existsById(productId)) {
            throw new BusinessException(ErrorType.NOT_FOUND_PRODUCT_ERROR);
        }
        if (!cartRepository.existsByMemberIdAndProductId(memberId, productId)) {
            throw new BusinessException(ErrorType.PRODUCT_NOT_IN_CART_ERROR);
        }
        cartRepository.deleteByMemberIdAndProductId(memberId, productId);

        return new CartCountResponse(cartRepository.findAllByMemberId(memberId).size());
    }

    @Transactional
    public CartCountResponse createProductInCart(Long productId, Long memberId) {
        if (!productRepository.existsById(productId)) {
            throw new BusinessException(ErrorType.NOT_FOUND_PRODUCT_ERROR);
        }
        if (cartRepository.existsByMemberIdAndProductId(memberId, productId)) {
            throw new BusinessException(ErrorType.PRODUCT_IN_CART_ERROR);
        }
        cartRepository.save(new CartEntity(memberId, productId));

        return new CartCountResponse(cartRepository.findAllByMemberId(memberId).size());
    }
}
