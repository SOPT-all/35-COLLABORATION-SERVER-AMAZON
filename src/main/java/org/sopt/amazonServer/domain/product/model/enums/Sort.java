package org.sopt.amazonServer.domain.product.model.enums;

import java.util.Comparator;
import java.util.HashMap;
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

    public static final Map<Sort, Comparator<ProductEntity>> COMPARATOR_MAP = new HashMap<>();
    private static final Map<String, Sort> SORT_MAP = new HashMap<>();

    static {
        for (Sort sort : Sort.values()) {
            SORT_MAP.put(sort.getValue(), sort);
        }
        COMPARATOR_MAP.put(Sort.REVIEW_COUNT, Comparator.comparing(ProductEntity::getReviewCount).reversed());
        COMPARATOR_MAP.put(Sort.LOW_PRICE, Comparator.comparing(ProductEntity::getPrice));
        COMPARATOR_MAP.put(Sort.LATEST_PRODUCTS, Comparator.comparing(ProductEntity::getLaunchDate).reversed());
        COMPARATOR_MAP.put(Sort.POPULARITY,
                Comparator.comparing(product -> (product.getSales() + product.getReviewCount()) * product.getRating()));
        COMPARATOR_MAP.put(Sort.SALES, Comparator.comparing(ProductEntity::getSales).reversed());
    }

    private final String value;

    Sort(String value) {
        this.value = value;
    }

    public static Sort fromValue(String value) {
        Sort sort = SORT_MAP.get(value);
        if (sort == null) {
            throw new BusinessException(ErrorType.INVALID_PRODUCTS_SORT_BY_ERROR);
        }
        return sort;
    }

    public String getValue() {
        return value;
    }
}