package com.farukcankaya.springcase.campaign;

import com.farukcankaya.springcase.TestUtils;
import com.farukcankaya.springcase.campaign.entity.Campaign;
import com.farukcankaya.springcase.campaign.entity.CategoryCampaign;
import com.farukcankaya.springcase.campaign.entity.DiscountType;
import com.farukcankaya.springcase.campaign.entity.ProductCampaign;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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
}
