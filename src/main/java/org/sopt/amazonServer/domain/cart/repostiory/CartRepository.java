package org.sopt.amazonServer.domain.cart.repostiory;

import java.util.List;
import org.sopt.amazonServer.domain.cart.model.entity.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<CartEntity, Long> {

    List<CartEntity> findAllByMemberId(Long memberId);

    void deleteByMemberIdAndProductId(Long memberId, Long productId);

    Boolean existsByMemberIdAndProductId(Long memberId, Long productId);
}
