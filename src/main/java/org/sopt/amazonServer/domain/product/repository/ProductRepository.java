package org.sopt.amazonServer.domain.product.repository;

import java.util.List;
import org.sopt.amazonServer.domain.product.model.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    List<ProductEntity> findByNameContainsIgnoreCase(String keyword);
}
