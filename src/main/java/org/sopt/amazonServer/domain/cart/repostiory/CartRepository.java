package org.sopt.amazonServer.domain.cart.repostiory;

import org.sopt.amazonServer.domain.cart.model.entity.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<CartEntity, Long> {

    boolean existsByMemberIdAndProductId(Long memberId, Long productId);
}
