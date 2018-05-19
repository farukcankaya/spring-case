package com.farukcankaya.springcase.campaign;

import com.farukcankaya.springcase.campaign.entity.Campaign;
import com.farukcankaya.springcase.common.ListResponse;
import com.farukcankaya.springcase.common.MissingParameterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v1/campaigns")
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

  @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseEntity<Campaign> create(@Valid @RequestBody Campaign campaign) {
    Campaign savedCampaign = campaignService.addCampaign(campaign);

    return new ResponseEntity<>(savedCampaign, HttpStatus.CREATED);
  }

  @PutMapping(
    value = "/{campaignId}",
    consumes = {MediaType.APPLICATION_JSON_VALUE}
  )
  @ResponseStatus(HttpStatus.OK)
  public ResponseEntity<Campaign> update(
      @PathVariable Long campaignId, @Valid @RequestBody Campaign campaign) {
    campaignService.updateCampaign(campaignId, campaign);
    return new ResponseEntity<>(campaign, HttpStatus.OK);
  }

  @DeleteMapping(value = "/{campaignId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public ResponseEntity<Campaign> destroy(@PathVariable Long campaignId) {
    campaignService.deleteCampaign(campaignId);
    return ResponseEntity.noContent().build();
  }
}
