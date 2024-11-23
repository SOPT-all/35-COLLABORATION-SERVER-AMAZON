package org.sopt.amazonServer.domain.product.model.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.sopt.amazonServer.domain.product.model.dto.CategoryResponse;
import org.sopt.amazonServer.domain.product.model.dto.OptionResponse;
import org.sopt.amazonServer.domain.product.model.entity.CategoryEntity;
import org.sopt.amazonServer.domain.product.model.entity.OptionEntity;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface FilterMapper {

    @Mapping(target = "optionList", source = "optionList")
    CategoryResponse toCategoryResponse(CategoryEntity categoryEntity, List<OptionResponse> optionList);

    OptionResponse toOptionResponse(OptionEntity optionEntity);
}
