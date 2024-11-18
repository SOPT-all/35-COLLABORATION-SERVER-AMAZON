package org.sopt.amazonServer.domain.product.model.enums;

import org.sopt.amazonServer.domain.product.model.entity.ProductEntity;
import org.sopt.amazonServer.global.exception.BusinessException;
import org.sopt.amazonServer.global.exception.ErrorType;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum Sort {

    POPULARITY("POPULARITY"),
    REVIEWCOUNT("REVIEWCOUNT"),
    SALES("SALES"),
    LOWPRICE("LOWPRICE"),
    LATESTPRODUCTS("LATESTPRODUCTS");

    private final String value;
    private static final Map<String, Sort> SORT_BY_MAP = new HashMap<>();


    Sort(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }


    static {
        for (Sort sort : Sort.values()) {
            SORT_BY_MAP.put(sort.getValue(), sort);
        }
    }
    public static Sort fromValue(String value) {
        Sort sort_by = SORT_BY_MAP.get(value);

        if (sort_by == null) {
            throw new BusinessException(ErrorType.INVALID_PRODUCTS_SORT_BY_ERROR);
        }

        return sort_by;
    }
}