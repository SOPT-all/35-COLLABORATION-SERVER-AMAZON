package org.sopt.amazonServer.domain.product.service;

import org.sopt.amazonServer.domain.product.model.dto.GetProductRequest;
import org.sopt.amazonServer.domain.product.model.entity.ProductEntity;
import org.sopt.amazonServer.domain.product.model.enums.Sort;
import org.sopt.amazonServer.domain.product.repository.ProductRepository;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<GetProductRequest> fetchProducts(String keyword, Sort sort, Long memberId) {
        // 1. 상품 데이터를 keyword를 기반으로 가져온다. ( keyword로 필터링하는건 repository에서 )
        // 2. 상품 데이터를 sort 기반으로 정렬한다.
        // 3. memberId를 가지고 온 다음에 해당 상품이 장바구니에 있는지 여부를 같이 넣어줌
        List<ProductEntity> productList = productRepository.findByNameContaining(keyword);
        sortProducts(productList,sort);
       return productList.stream()
                .map(product -> new GetProductRequest(
                        product.getId(),
                        product.getImage(),
                        product.getBrand(),
                        product.getName(),
                        product.isBestSeller(),
                        product.getRating(),
                        product.getReviewCount(),
                        product.getPrice(),
                        product.getDiscountRate() != null ? product.getDiscountRate() : 0,
                        product.isFreeDelivery(),
                        product.getDeliveryDate().toLocalDateTime()
                        .toLocalDate()
                        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                        product.getFreeDeliveryStandard() != null ? product.getFreeDeliveryStandard() : 0,
                        false // 장바구니 여부
                ))
                .toList();
    }


    public final void sortProducts(List<ProductEntity> productList, Sort sort) {
        Map<Sort, Comparator<ProductEntity>> comparatorMap = new HashMap<>();

        comparatorMap.put(Sort.REVIEWCOUNT, Comparator.comparing(ProductEntity::getReviewCount).reversed());
        comparatorMap.put(Sort.LOWPRICE, Comparator.comparing(ProductEntity::getPrice));
        comparatorMap.put(Sort.LATESTPRODUCTS, Comparator.comparing(ProductEntity::getLaunchDate).reversed());
        comparatorMap.put(Sort.POPULARITY,Comparator.comparing(ProductEntity::getSales).reversed());

        Comparator<ProductEntity> comparator = comparatorMap.get(
                sort
        );
        productList.sort(comparator);
    }
}

