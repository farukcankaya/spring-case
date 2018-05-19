package com.farukcankaya.springcase.campaign;

import com.farukcankaya.springcase.campaign.entity.Campaign;
import com.farukcankaya.springcase.campaign.entity.DiscountType;
import com.farukcankaya.springcase.common.MissingParameterException;
import com.farukcankaya.springcase.common.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CampaignService {
  private CampaignRepository campaignRepository;

  @Autowired
  public CampaignService(CampaignRepository campaignRepository) {
    this.campaignRepository = campaignRepository;
  }

  public List<Campaign> getAllCampaigns() {
    List<Campaign> list = new ArrayList<>();
    this.campaignRepository.findAll().forEach(campaign -> list.add(campaign));
    return list;
  }

  public Campaign getCampaignById(Long id) {
    return campaignRepository.findById(id).orElseThrow(() -> new NotFoundException(Campaign.class));
  }

  public synchronized Campaign addCampaign(Campaign campaign) {
    if (campaign.getDiscountType().equals(DiscountType.RATE)
        && campaign.getMaximumDiscountPrice() == null) {
      throw new MissingParameterException("maximumDiscountPrice");
    }

    return campaignRepository.save(campaign);
  }
}
