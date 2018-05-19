package com.farukcankaya.springcase.campaign;

import com.farukcankaya.springcase.TestUtils;
import com.farukcankaya.springcase.campaign.entity.Campaign;
import com.farukcankaya.springcase.campaign.entity.CategoryCampaign;
import com.farukcankaya.springcase.campaign.entity.DiscountType;
import com.farukcankaya.springcase.campaign.entity.ProductCampaign;
import com.farukcankaya.springcase.common.NotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CampaignServiceTest {
  @Mock CampaignRepository campaignRepositoryMock;

  @InjectMocks CampaignService campaignService;

  @Test
  public void givenCampaignList_whenGetAllCampaigns_thenReturnCampaignList() {
    List<Campaign> campaignList = TestUtils.getRandomCampaigns(2);

    when(campaignRepositoryMock.findAll()).thenReturn(campaignList);

    assertThat(campaignService.getAllCampaigns()).isEqualTo(campaignList);
  }

  @Test(expected = NotFoundException.class)
  public void givenNull_whenGetCampaignById_thenThowNotFoundException() {
    Campaign campaign = TestUtils.getRandomCampaigns(1).get(0);

    when(campaignRepositoryMock.findById(campaign.getId())).thenReturn(Optional.empty());

    campaignService.getCampaignById(campaign.getId());
  }

  @Test
  public void givenCampaign_whenGetCampaignById_thenReturnCampaign() {
    Campaign campaign = TestUtils.getRandomCampaigns(1).get(0);

    when(campaignRepositoryMock.findById(campaign.getId())).thenReturn(Optional.of(campaign));

    assertThat(campaignService.getCampaignById(campaign.getId())).isEqualTo(campaign);
  }
}
