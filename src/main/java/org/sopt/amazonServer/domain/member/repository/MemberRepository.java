package org.sopt.amazonServer.domain.member.repository;

import org.sopt.amazonServer.domain.member.model.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
}
