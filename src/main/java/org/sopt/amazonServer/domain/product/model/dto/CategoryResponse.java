package org.sopt.amazonServer.domain.product.model.dto;

import java.util.List;

public record CategoryResponse(
        Long id,
        String name,
        List<OptionResponse> optionList
) {

}
