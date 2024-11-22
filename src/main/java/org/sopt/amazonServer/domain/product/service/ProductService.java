package org.sopt.amazonServer.domain.product.service;

import static org.sopt.amazonServer.domain.product.model.enums.Sort.COMPARATOR_MAP;

import java.util.Comparator;
import java.util.List;
import org.sopt.amazonServer.domain.cart.model.entity.CartEntity;
import org.sopt.amazonServer.domain.cart.repostiory.CartRepository;
import org.sopt.amazonServer.domain.member.repository.MemberRepository;
import org.sopt.amazonServer.domain.product.model.dto.GetProductResponse;
import org.sopt.amazonServer.domain.product.model.entity.ProductEntity;
import org.sopt.amazonServer.domain.product.model.enums.Sort;
import org.sopt.amazonServer.domain.product.model.mapper.ProductMapper;
import org.sopt.amazonServer.domain.product.repository.ProductRepository;
import org.sopt.amazonServer.global.exception.BusinessException;
import org.sopt.amazonServer.global.exception.ErrorType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final MemberRepository memberRepository;
    private final ProductMapper productMapper;

    public ProductService(ProductRepository productRepository, CartRepository cartRepository,
                          MemberRepository memberRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
        this.memberRepository = memberRepository;
        this.productMapper = productMapper;
    }

    @Transactional(readOnly = true)
    public List<GetProductResponse> fetchProducts(final String keyword, final Sort sort, final Long memberId) {
        if (!memberRepository.existsById(memberId)) {
            throw new BusinessException(ErrorType.NOT_FOUND_MEMBER_ERROR);
        }
        List<ProductEntity> productList;
        if (keyword == null) { // 키워드가 없을 땐 전체 데이터 조회
            productList = productRepository.findAll();
        } else {
            productList = productRepository.findByNameContainsIgnoreCase(keyword);
        }
        // 상품 정렬
        sortProducts(productList, sort);
        List<CartEntity> cartList = cartRepository.findAllByMemberId(memberId);

        return productList.stream()
                .map(product -> productMapper.toGetProductResponse(product,
                        cartList.stream().anyMatch(cart -> cart.getProductId().equals(product.getId()))
                ))
                .toList();
    }

    public void sortProducts(List<ProductEntity> productList, Sort sort) {
        Comparator<ProductEntity> comparator = COMPARATOR_MAP.get(
                sort
        );
        productList.sort(comparator);
    }

}

