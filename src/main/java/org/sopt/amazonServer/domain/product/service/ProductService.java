package org.sopt.amazonServer.domain.product.service;

import static org.sopt.amazonServer.domain.product.model.enums.Sort.COMPARATOR_MAP;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.sopt.amazonServer.domain.cart.model.entity.CartEntity;
import org.sopt.amazonServer.domain.cart.repostiory.CartRepository;
import org.sopt.amazonServer.domain.member.repository.MemberRepository;
import org.sopt.amazonServer.domain.product.model.dto.CategoryResponse;
import org.sopt.amazonServer.domain.product.model.dto.GetProductResponse;
import org.sopt.amazonServer.domain.product.model.dto.OptionResponse;
import org.sopt.amazonServer.domain.product.model.entity.CategoryEntity;
import org.sopt.amazonServer.domain.product.model.entity.OptionEntity;
import org.sopt.amazonServer.domain.product.model.entity.ProductEntity;
import org.sopt.amazonServer.domain.product.model.enums.Sort;
import org.sopt.amazonServer.domain.product.model.mapper.FilterMapper;
import org.sopt.amazonServer.domain.product.model.mapper.ProductMapper;
import org.sopt.amazonServer.domain.product.repository.CategoryRepository;
import org.sopt.amazonServer.domain.product.repository.OptionRepository;
import org.sopt.amazonServer.domain.product.repository.ProductRepository;
import org.sopt.amazonServer.global.exception.BusinessException;
import org.sopt.amazonServer.global.exception.ErrorType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final OptionRepository optionRepository;

    private final CartRepository cartRepository;
    private final MemberRepository memberRepository;

    private final ProductMapper productMapper;
    private final FilterMapper filterMapper;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository,
                          OptionRepository optionRepository, CartRepository cartRepository,
                          MemberRepository memberRepository, ProductMapper productMapper,
                          FilterMapper filterMapper) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.optionRepository = optionRepository;
        this.cartRepository = cartRepository;
        this.memberRepository = memberRepository;
        this.productMapper = productMapper;
        this.filterMapper = filterMapper;
    }

    @Transactional(readOnly = true)
    public List<GetProductResponse> fetchProducts(
            final String keyword,
            final Sort sort,
            final Long memberId
    ) {
        if (!memberRepository.existsById(memberId)) {
            throw new BusinessException(ErrorType.NOT_FOUND_MEMBER_ERROR);
        }

        List<ProductEntity> productList;

        // 검색어가 없을 땐 전체 데이터 조회
        if (keyword == null) {
            productList = productRepository.findAll();
        } else {
            productList = productRepository.findByNameContainsIgnoreCase(keyword);
        }

        sortProducts(productList, sort);
        List<CartEntity> cartList = cartRepository.findAllByMemberId(memberId);

        return productList.stream()
                .map(product -> productMapper.toGetProductResponse(
                        product, cartList.stream().anyMatch(cart -> cart.getProductId().equals(product.getId()))
                ))
                .toList();
    }

    public void sortProducts(List<ProductEntity> productList, Sort sort) {
        Comparator<ProductEntity> comparator = COMPARATOR_MAP.get(sort);
        productList.sort(comparator);
    }

    @Transactional(readOnly = true)
    public List<CategoryResponse> fetchCategories() {
        List<CategoryEntity> categories = categoryRepository.findAll();
        Map<Long, List<OptionResponse>> optionsByCategory = optionRepository.findAll().stream()
                .collect(Collectors.groupingBy(
                        OptionEntity::getCategoryId,
                        Collectors.mapping(filterMapper::toOptionResponse, Collectors.toList())
                ));

        return categories.stream()
                .map(category -> {
                    List<OptionResponse> optionList = optionsByCategory.getOrDefault(category.getId(),
                            Collections.emptyList());

                    return filterMapper.toCategoryResponse(category, optionList);
                })
                .filter(categoryResponse -> !categoryResponse.optionList().isEmpty())
                .toList();
    }
}
