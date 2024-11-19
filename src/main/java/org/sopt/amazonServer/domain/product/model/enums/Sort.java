package org.sopt.amazonServer.domain.product.model.enums;

import java.util.HashMap;
import java.util.Map;
import org.sopt.amazonServer.global.exception.BusinessException;
import org.sopt.amazonServer.global.exception.ErrorType;

public enum Sort {
    POPULARITY("POPULARITY"),
    REVIEW_COUNT("REVIEWCOUNT"),
    SALES("SALES"),
    LOW_PRICE("LOWPRICE"),
    LATEST_PRODUCTS("LATESTPRODUCTS");

    private static final Map<String, Sort> SORT_BY_MAP = new HashMap<>();

    static {
        for (Sort sort : Sort.values()) {
            SORT_BY_MAP.put(sort.getValue(), sort);
        }
    }

    private final String value;

    Sort(String value) {
        this.value = value;
    }

    public static Sort fromValue(String value) {
        Sort sort_by = SORT_BY_MAP.get(value);
        if (sort_by == null) {
            throw new BusinessException(ErrorType.INVALID_PRODUCTS_SORT_BY_ERROR);
        }
        return sort_by;
    }

    public String getValue() {
        return value;
    }
}