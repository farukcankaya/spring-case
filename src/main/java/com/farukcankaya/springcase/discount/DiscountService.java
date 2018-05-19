package com.farukcankaya.springcase.discount;

import com.farukcankaya.springcase.campaign.CampaignRepository;
import com.farukcankaya.springcase.discount.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiscountService {
  private CampaignRepository campaignRepository;

  public DiscountService() {}

  @Autowired
  public DiscountService(CampaignRepository campaignRepository) {
    this.campaignRepository = campaignRepository;
  }

  public List<Product> calculateDiscounts(List<Product> items) {
    return items;
  }
}
