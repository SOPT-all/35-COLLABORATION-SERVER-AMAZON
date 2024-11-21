package org.sopt.amazonServer.domain.member.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.sopt.amazonServer.domain.member.model.dto.SearchHistoryResponse;
import org.sopt.amazonServer.domain.member.model.entity.SearchHistoryEntity;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface SearchHistoryMapper {

    // convert SearchHistoryEntity to SearchHistoryResponse
    @Mapping(target = "searchDate", source = "updatedAt", dateFormat = "MM.dd")
    SearchHistoryResponse toSearchHistoryResponse(SearchHistoryEntity searchHistoryEntity);
}
