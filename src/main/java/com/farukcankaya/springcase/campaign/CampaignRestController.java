package com.farukcankaya.springcase.campaign;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/campaigns")
public class CampaignRestController {

  private CampaignService campaignService;

  @Autowired
  public CampaignRestController(CampaignService campaignService) {
    this.campaignService = campaignService;
  }
}
