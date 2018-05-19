package com.farukcankaya.springcase.campaign;

import com.farukcankaya.springcase.campaign.entity.Campaign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
public interface CampaignRepository extends JpaRepository<Campaign, Long> {
  @Query("select u from #{#entityName} u where u.productId = ?1")
  Optional<Campaign> findByProductId(Long productId);

  @Query("select u from #{#entityName} u where u.categoryId = ?1")
  Optional<Campaign> findByCategoryId(Long categoryId);
}
