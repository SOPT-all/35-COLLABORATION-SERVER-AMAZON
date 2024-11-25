package org.sopt.amazonServer.domain.cart.service;

import jakarta.transaction.Transactional;
import org.sopt.amazonServer.domain.cart.model.dto.CartCountResponse;
import org.sopt.amazonServer.domain.cart.model.entity.CartEntity;
import org.sopt.amazonServer.domain.cart.repostiory.CartRepository;
import org.sopt.amazonServer.domain.member.repository.MemberRepository;
import org.sopt.amazonServer.domain.product.repository.ProductRepository;
import org.sopt.amazonServer.global.exception.BusinessException;
import org.sopt.amazonServer.global.exception.ErrorType;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;

    public CartService(CartRepository cartRepository, ProductRepository productRepository,
                       MemberRepository memberRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.memberRepository = memberRepository;
    }

    @Transactional
    public CartCountResponse removeProductInCart(Long productId, Long memberId) {
        if (!memberRepository.existsById(memberId)) {
            throw new BusinessException(ErrorType.NOT_FOUND_MEMBER_ERROR);
        }
        if (!productRepository.existsById(productId)) {
            throw new BusinessException(ErrorType.NOT_FOUND_PRODUCT_ERROR);
        }
        if (!cartRepository.existsByMemberIdAndProductId(memberId, productId)) {
            throw new BusinessException(ErrorType.PRODUCT_NOT_IN_CART_ERROR);
        }
        cartRepository.deleteByMemberIdAndProductId(memberId, productId);

        return CartCountResponse.fromCartCount(cartRepository.findAllByMemberId(memberId).size());
    }

    @Transactional
    public CartCountResponse createProductInCart(Long productId, Long memberId) {
        if (!memberRepository.existsById(memberId)) {
            throw new BusinessException(ErrorType.NOT_FOUND_MEMBER_ERROR);
        }
        if (!productRepository.existsById(productId)) {
            throw new BusinessException(ErrorType.NOT_FOUND_PRODUCT_ERROR);
        }
        if (cartRepository.existsByMemberIdAndProductId(memberId, productId)) {
            throw new BusinessException(ErrorType.PRODUCT_IN_CART_ERROR);
        }
        cartRepository.save(CartEntity.of(memberId, productId));

        return CartCountResponse.fromCartCount(cartRepository.findAllByMemberId(memberId).size());
    }
}
