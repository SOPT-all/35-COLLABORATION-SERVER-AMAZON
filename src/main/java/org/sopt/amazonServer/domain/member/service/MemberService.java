package org.sopt.amazonServer.domain.member.service;

import java.util.List;
import java.util.stream.Collectors;
import org.sopt.amazonServer.domain.member.model.dto.SearchHistoryResponse;
import org.sopt.amazonServer.domain.member.model.mapper.SearchHistoryMapper;
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

    private final SearchHistoryMapper searchHistoryMapper;

    public MemberService(
            MemberRepository memberRepository,
            SearchHistoryRepository searchHistoryRepository,
            SearchHistoryMapper searchHistoryMapper
    ) {
        this.memberRepository = memberRepository;
        this.searchHistoryRepository = searchHistoryRepository;
        this.searchHistoryMapper = searchHistoryMapper;
    }

    // 특정 회원의 최근 검색 기록 목록을 반환 (최대 5개)
    @Transactional(readOnly = true)
    public List<SearchHistoryResponse> fetchSearchHistoryList(final Long memberId) {
        if (!memberRepository.existsById(memberId)) {
            throw new BusinessException(ErrorType.NOT_FOUND_MEMBER_ERROR);
        }

        return searchHistoryRepository.findTop5ByMemberIdOrderByUpdatedAtDesc(memberId).stream()
                .map(searchHistoryMapper::toSearchHistoryResponse)
                .collect(Collectors.toList());
    }
}
