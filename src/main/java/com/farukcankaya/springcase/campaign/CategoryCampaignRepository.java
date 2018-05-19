package com.farukcankaya.springcase.campaign;

import com.farukcankaya.springcase.campaign.entity.CategoryCampaign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
public interface CategoryCampaignRepository extends JpaRepository<CategoryCampaign, Long> {
  @Query("SELECT p FROM CategoryCampaign p WHERE p.categoryId=?1")
  Optional<CategoryCampaign> findByCategoryId(Long categoryId);
}
