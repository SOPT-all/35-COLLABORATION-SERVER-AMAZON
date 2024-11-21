package org.sopt.amazonServer.domain.member.model.dto;

public record SearchHistoryResponse(
        String keyword,
        String searchDate
) {

}
