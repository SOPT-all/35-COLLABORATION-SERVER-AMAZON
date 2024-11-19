package org.sopt.amazonServer.domain.product.service;

import org.sopt.amazonServer.domain.cart.repostiory.CartRepository;
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
    private final CartRepository cartRepository;

    public ProductService(ProductRepository productRepository,CartRepository cartRepository) {
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
    }

    public List<GetProductRequest> fetchProducts(String keyword, Sort sort, Long memberId) {
        List<ProductEntity> productList = productRepository.findByNameContaining(keyword);
        sortProducts(productList,sort);
       return productList.stream()
                .map(product -> {
                    return new GetProductRequest(
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
                        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                        product.getFreeDeliveryStandard(),
                            cartRepository.existsByMemberIdAndProductId(memberId,product.getId()) // 장바구니 여부
                );})
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

