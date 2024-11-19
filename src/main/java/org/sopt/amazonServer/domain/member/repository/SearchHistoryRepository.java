package org.sopt.amazonServer.domain.member.repository;

import java.util.List;
import org.sopt.amazonServer.domain.member.model.entity.SearchHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SearchHistoryRepository extends JpaRepository<SearchHistory, Long> {

    List<SearchHistory> findTop5ByMemberIdOrderByUpdatedAtDesc(Long memberId);
}
