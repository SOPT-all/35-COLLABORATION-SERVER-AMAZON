package org.sopt.amazonServer.domain.member.repository;

import java.util.List;
import org.sopt.amazonServer.domain.member.model.entity.SearchHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SearchHistoryRepository extends JpaRepository<SearchHistoryEntity, Long> {

    List<SearchHistoryEntity> findTop5ByMemberIdOrderByUpdatedAtDesc(Long memberId);
}
