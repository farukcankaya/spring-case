package com.farukcankaya.springcase.campaign;

import com.farukcankaya.springcase.campaign.entity.Campaign;
import com.farukcankaya.springcase.common.ListResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

  @GetMapping(
    value = "/{campaignId}",
    produces = {MediaType.APPLICATION_JSON_VALUE}
  )
  @ResponseStatus(HttpStatus.OK)
  public ResponseEntity<Campaign> show(@PathVariable Long campaignId) {
    Campaign campaign = this.campaignService.getCampaignById(campaignId);

    return new ResponseEntity<>(campaign, HttpStatus.OK);
  }
}
