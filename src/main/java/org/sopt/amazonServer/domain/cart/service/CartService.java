package org.sopt.amazonServer.domain.cart.service;

import jakarta.transaction.Transactional;
import java.util.List;
import org.sopt.amazonServer.domain.cart.model.dto.CartResponse;
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
    public CartResponse removeProduct(Long productId, Long memberId) {
        if (!productRepository.existsById(productId)) {
            throw new BusinessException(ErrorType.NOT_FOUND_PRODUCT_ERROR);
        }
        if (!cartRepository.existsByMemberIdAndProductId(memberId, productId)) {
            throw new BusinessException(ErrorType.PRODUCT_NOT_IN_CART_ERROR);
        }
        cartRepository.deleteByMemberIdAndProductId(memberId, productId);
        List<CartEntity> cartList = cartRepository.findAllByMemberId(memberId);

        return new CartResponse(cartList.size());

    }
}
