package com.farukcankaya.springcase.campaign;

import com.farukcankaya.springcase.campaign.entity.Campaign;
import com.farukcankaya.springcase.common.ListResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/campaigns")
public class CampaignRestController {

  private CampaignService campaignService;

  @Autowired
  public CampaignRestController(CampaignService campaignService) {
    this.campaignService = campaignService;
  }

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public ListResponse<Campaign> index() {
    List<Campaign> campaignList = campaignService.getAllCampaigns();

    return new ListResponse<>(campaignList);
  }
}
