package org.sopt.amazonServer.domain.product.repository;

import org.sopt.amazonServer.domain.product.model.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {

}
