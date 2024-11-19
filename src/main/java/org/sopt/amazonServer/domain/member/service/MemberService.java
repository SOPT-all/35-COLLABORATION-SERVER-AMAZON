package org.sopt.amazonServer.domain.member.service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import org.sopt.amazonServer.domain.member.model.dto.SearchHistoryResponse;
import org.sopt.amazonServer.domain.member.model.entity.SearchHistory;
import org.sopt.amazonServer.domain.member.repository.MemberRepository;
import org.sopt.amazonServer.domain.member.repository.SearchHistoryRepository;
import org.sopt.amazonServer.global.exception.BusinessException;
import org.sopt.amazonServer.global.exception.ErrorType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final SearchHistoryRepository searchHistoryRepository;

    // 날짜를 "MM.dd" 형식으로 변환하는 데 사용
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("MM.dd");

    public MemberService(MemberRepository memberRepository, SearchHistoryRepository searchHistoryRepository) {
        this.memberRepository = memberRepository;
        this.searchHistoryRepository = searchHistoryRepository;
    }

    // 특정 회원의 최근 검색 기록 목록을 반환 (최대 5개)
    @Transactional(readOnly = true)
    public List<SearchHistoryResponse> fetchSearchHistoryList(final Long memberId) {
        if (!memberRepository.existsById(memberId)) {
            throw new BusinessException(ErrorType.NOT_FOUND_MEMBER_ERROR);
        }

        return searchHistoryRepository.findTop5ByMemberIdOrderByUpdatedAtDesc(memberId).stream()
                .map(this::convertToSearchHistoryResponse)
                .collect(Collectors.toList());
    }

    // SearchHistory 엔티티를 SearchHistoryResponse DTO의 형태로 변환
    private SearchHistoryResponse convertToSearchHistoryResponse(SearchHistory searchHistory) {
        return new SearchHistoryResponse(
                searchHistory.getKeyword(),
                searchHistory.getUpdatedAt().format(DATE_TIME_FORMATTER)
        );
    }
}
