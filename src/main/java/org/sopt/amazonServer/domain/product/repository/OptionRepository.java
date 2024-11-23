package org.sopt.amazonServer.domain.product.repository;

import org.sopt.amazonServer.domain.product.model.entity.OptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OptionRepository extends JpaRepository<OptionEntity, Long> {

}
