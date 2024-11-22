package org.sopt.amazonServer.domain.product.model.enums;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.sopt.amazonServer.domain.product.model.entity.ProductEntity;
import org.sopt.amazonServer.global.exception.BusinessException;
import org.sopt.amazonServer.global.exception.ErrorType;

public enum Sort {
    POPULARITY("POPULARITY"),
    REVIEW_COUNT("REVIEWCOUNT"),
    SALES("SALES"),
    LOW_PRICE("LOWPRICE"),
    LATEST_PRODUCTS("LATESTPRODUCTS");

    private static final Map<String, Sort> sortMap = new HashMap<>();
    private final static Map<Sort, Comparator<ProductEntity>> comparatorMap = new HashMap<>();

    static {
        for (Sort sort : Sort.values()) {
            sortMap.put(sort.getValue(), sort);
        }
        comparatorMap.put(Sort.REVIEW_COUNT, Comparator.comparing(ProductEntity::getReviewCount).reversed());
        comparatorMap.put(Sort.LOW_PRICE, Comparator.comparing(ProductEntity::getPrice));
        comparatorMap.put(Sort.LATEST_PRODUCTS, Comparator.comparing(ProductEntity::getLaunchDate).reversed());
        comparatorMap.put(Sort.POPULARITY,
                Comparator.comparing(product -> (product.getSales() + product.getReviewCount()) * product.getRating()));
        comparatorMap.put(Sort.SALES, Comparator.comparing(ProductEntity::getSales).reversed());
    }

    private final String value;

    Sort(String value) {
        this.value = value;
    }

    public static Sort fromValue(String value) {
        Sort sort = sortMap.get(value);
        if (sort == null) {
            throw new BusinessException(ErrorType.INVALID_PRODUCTS_SORT_BY_ERROR);
        }
        return sort;
    }

    public static void sortProducts(List<ProductEntity> productList, Sort sort) {
        Comparator<ProductEntity> comparator = comparatorMap.get(
                sort
        );
        productList.sort(comparator);
    }

    public String getValue() {
        return value;
    }
}