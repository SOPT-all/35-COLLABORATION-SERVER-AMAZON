package org.sopt.amazonServer.domain.product.service;

import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.sopt.amazonServer.domain.cart.repostiory.CartRepository;
import org.sopt.amazonServer.domain.member.repository.MemberRepository;
import org.sopt.amazonServer.domain.product.model.dto.GetProductResponse;
import org.sopt.amazonServer.domain.product.model.entity.ProductEntity;
import org.sopt.amazonServer.domain.product.model.enums.Sort;
import org.sopt.amazonServer.domain.product.repository.ProductRepository;
import org.sopt.amazonServer.global.exception.BusinessException;
import org.sopt.amazonServer.global.exception.ErrorType;
import org.springframework.stereotype.Component;

@Component
public class ProductService {
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final MemberRepository memberRepository;

    public ProductService(ProductRepository productRepository, CartRepository cartRepository,
                          MemberRepository memberRepository) {
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
        this.memberRepository = memberRepository;
    }

    public List<GetProductResponse> fetchProducts(String keyword, Sort sort, Long memberId) {
        if (!memberRepository.existsById(memberId)) {
            throw new BusinessException(ErrorType.NOT_FOUND_MEMBER);
        }
        List<ProductEntity> productList;
        if (keyword == null) { // 키워드가 없을 땐 전체 데이터 조회
            productList = productRepository.findAll();
        } else {
            productList = productRepository.findByNameContaining(keyword);
        }
        // 상품 정렬
        sortProducts(productList, sort);

        return productList.stream()
                .map(product -> new GetProductResponse(
                        product.getId(),
                        product.getImage(),
                        product.getBrand(),
                        product.getName(),
                        product.isBestSeller(),
                        product.getRating(),
                        product.getReviewCount(),
                        product.getPrice(),
                        product.getDiscountRate(),
                        product.isFreeDelivery(),
                        product.getDeliveryDate().toLocalDateTime()
                                .toLocalDate()
                                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd")), // TODO: DateFormatter 분리
                        product.getFreeDeliveryStandard(),
                        cartRepository.existsByMemberIdAndProductId(memberId, product.getId())
                ))
                .toList();
    }


    private void sortProducts(List<ProductEntity> productList, Sort sort) {
        Map<Sort, Comparator<ProductEntity>> comparatorMap = new HashMap<>();

        comparatorMap.put(Sort.REVIEW_COUNT, Comparator.comparing(ProductEntity::getReviewCount).reversed());
        comparatorMap.put(Sort.LOW_PRICE, Comparator.comparing(ProductEntity::getPrice));
        comparatorMap.put(Sort.LATEST_PRODUCTS, Comparator.comparing(ProductEntity::getLaunchDate).reversed());
        comparatorMap.put(Sort.POPULARITY,
                Comparator.comparing(product -> (product.getSales() + product.getReviewCount()) * product.getRating()));
        comparatorMap.put(Sort.SALES, Comparator.comparing(ProductEntity::getSales).reversed());

        Comparator<ProductEntity> comparator = comparatorMap.get(
                sort
        );
        productList.sort(comparator);
    }
}

